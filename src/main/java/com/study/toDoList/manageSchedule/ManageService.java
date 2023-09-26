package com.study.toDoList.manageSchedule;

import com.study.toDoList.domain.*;
import com.study.toDoList.manageSchedule.dto.CreateRequestDto;
import com.study.toDoList.manageSchedule.dto.CreateResponseDto;
import com.study.toDoList.manageSchedule.dto.UpdateScheduleRequestDto;
import com.study.toDoList.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ManageService {
    private final ScheduleRepository scheduleRepository;
    private final ContentRepository contentRepository;
    private final UserRepository userRepository;

    public LocalDate StringtoDate(String date){
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTime = LocalDate.parse(date, formatter);
        return dateTime;
    }

    @Transactional
    public CreateResponseDto createSchedule(CreateRequestDto createRequestDto){
        String email = createRequestDto.getEmail();
        List<String> contents = createRequestDto.getContents();
        LocalDate closingDate = null;
        if (createRequestDto.getClosingDate() != null) {
            closingDate = StringtoDate(createRequestDto.getClosingDate());
        }
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("해당 아이디가 없습니다."));
        Schedule schedule = Schedule.builder()
                .user(user).type(createRequestDto.getType())
                .day(createRequestDto.getDay())
                .closingDate(closingDate)
                .complete('N').build();
        Schedule saveSchedule = scheduleRepository.save(schedule);
        //content save
        List<Content> saveContents=new ArrayList<>();
        for (String content:contents){
            Content newContent = Content.builder().content(content).status('N').schedule_id(saveSchedule.getId()).build();
            saveContents.add(newContent);
        }
        contentRepository.saveAll(saveContents);

        CreateResponseDto createResponseDto = CreateResponseDto.builder().schedule(saveSchedule).contents(saveContents).build();
        return createResponseDto;
    }

    @Transactional
    public Map<String,String> updateContentCheckList(Long content_id, Character status){
        Content content = contentRepository.findById(content_id)
                .orElseThrow(() -> new IllegalArgumentException("해당 할 일 내역이 없습니다." + content_id));
        //할일 리스트 업데이트(체크표시, 표시안함)
        content.update(status);
        Long schedule_Id=content.getSchedule_id();
        //해당 schedule스케줄 중 나의 스케줄을 가지고 리스트들을 찾는다.
        List<Content> contents = contentRepository.findByScheduleId(schedule_Id);

        //결과 반환 할 것 모두의 스케줄이 완료된다면 Y를 보내고 아니라면 N를 보낸다.
        Map<String,String> result=new HashMap<>();
        result.put("allcomplete", "N");
        if (checkComplete(schedule_Id,contents)){
            result.put("allcomplete","Y");
        }
        return result;
    }

    @Transactional
    public ResponseEntity<Map<String,Boolean>> updateSchedule(Long schedule_id,UpdateScheduleRequestDto dto){
        Schedule schedule = scheduleRepository.findById(schedule_id)
                .orElseThrow(() -> new IllegalArgumentException("해당 스케줄이 없습니다." + schedule_id));

        LocalDate closingDate = null;
        if (dto.getClosingDate() != null) {
            closingDate = StringtoDate(dto.getClosingDate());
        }
        //스케줄 업데이트
        schedule.update(dto.getType(),dto.getDay(),closingDate);
        //스케줄에 있는 리스트 다 삭제
        List<Content> oldContents = contentRepository.findByScheduleId(schedule_id);
        for (Content oldContent:oldContents){
            contentRepository.delete(oldContent);
        }
        //다시 리스트 저장
        List<Content> newcontents=dto.getContents();
        for(Content content:newcontents){
            Content newContent = Content.builder().content(content.getContent())
                    .status(content.getStatus()).schedule_id(schedule_id).build();
            contentRepository.save(newContent);
        }
        //리스트가 모두 Y인지 확인하고 업데이트
        checkComplete(schedule_id, newcontents);
        return makeResponse("update");
    }

    @Transactional
    public ResponseEntity<Map<String,Boolean>> deleteSchedule(Long schedule_id){
        Schedule schedule = scheduleRepository.findById(schedule_id)
                .orElseThrow(() -> new IllegalArgumentException("해당 스케줄이 없습니다" + schedule_id));
        scheduleRepository.delete(schedule);
        return makeResponse("delete");
    }

    public boolean checkComplete(Long schedule_id, List<Content> contents){
        Schedule schedule = scheduleRepository.findById(schedule_id)
            .orElseThrow(()->new IllegalArgumentException("해당 스케줄이 없습니다"));
        //만약 스케줄 할 일 리스트들 중 complete된게 없다면 업데이트 안함
        for (Content i: contents){
            if (!i.getStatus().equals('Y')){
                schedule.update('N');
                return false;
            }
        }
        //만약 스케줄 리스트를 다 complete했다면 나의 스케줄 complete으로 만든다.
        schedule.update('Y');
        return true;
    }

    public ResponseEntity<Map<String, Boolean>> makeResponse(String state){
        Map<String, Boolean> response = new HashMap<>();
        response.put(state, Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}

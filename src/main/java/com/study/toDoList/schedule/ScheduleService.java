package com.study.toDoList.schedule;

import com.study.toDoList.domain.*;
import com.study.toDoList.repository.*;
import com.study.toDoList.schedule.dto.ScheduleResponseDto;
import com.study.toDoList.schedule.dto.ScheduleResponseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ContentRepository contentRepository;

    @Transactional
    public ScheduleResponseDto getMySchedules(User user) {
        List<Schedule> schedules = scheduleRepository.findByUserId(user.getId());
        List<ScheduleInterface> deadlineSchedules=scheduleRepository.findByDeadLine(user.getId());
        List<Long> closeDealineList=new ArrayList<>();
        for (ScheduleInterface schedule:deadlineSchedules){
            closeDealineList.add(schedule.getId());
        }
        //스케줄 정보담기
        List<ScheduleResponseVo> scheduleList=returnResponse(schedules);
        ScheduleResponseDto result = ScheduleResponseDto.builder()
                .user(user)
                .scheduleList(scheduleList)
                .closeDeadline(closeDealineList).build();
        return result;
    }

    //할 일 제목,할 일 리스트 보여주기
    public List<ScheduleResponseVo> returnResponse(List<Schedule> schedules){
        List<ScheduleResponseVo> response = new ArrayList<>();
        //스케줄 리스트 돈다
        for (Schedule schedule:schedules) {
            List<Content> contents = new ArrayList<>();
            List<Content> contentsBySchedule = contentRepository.findByScheduleId(schedule.getId());
            for (Content content:contentsBySchedule) {
                contents.add(content);
            }
            ScheduleResponseVo scheduleResponseVo = ScheduleResponseVo.builder()
                    .id(schedule.getId())
                    .type(schedule.getType()).closingDate(schedule.getClosingDate())
                    .allcomplete(schedule.getComplete()).content(contents).build();
                response.add(scheduleResponseVo);
        }
        return response;
    }

}

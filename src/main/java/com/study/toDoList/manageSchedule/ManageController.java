package com.study.toDoList.manageSchedule;
import com.study.toDoList.manageSchedule.dto.CreateRequestDto;
import com.study.toDoList.manageSchedule.dto.CreateResponseDto;
import com.study.toDoList.manageSchedule.dto.UpdateScheduleRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class ManageController {
    private final ManageService manageService;

    @PostMapping("/api/create")
    public CreateResponseDto createSchedule(@RequestBody CreateRequestDto createRequestDto){
        return manageService.createSchedule(createRequestDto);
    }

    @PostMapping("/api/update/check")
    public Map<String, String> updateCheckContent(@RequestParam Long content_id, @RequestParam Character status){
        return manageService.updateContentCheckList(content_id,status);
    }

    @PostMapping("api/update/{id}")
    public ResponseEntity<Map<String,Boolean>> updateSchedule(@PathVariable("id") Long schedule_id
            , @RequestBody UpdateScheduleRequestDto updateScheduleRequestDto){
        return manageService.updateSchedule(schedule_id,updateScheduleRequestDto);

    }

    @DeleteMapping("api/delete/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteSchedule(@PathVariable("id") Long schedule_id){
        return manageService.deleteSchedule(schedule_id);

    }
}

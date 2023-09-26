package com.study.toDoList.schedule;

import com.study.toDoList.Login.AuthService;
import com.study.toDoList.domain.User;
import com.study.toDoList.repository.UserRepository;
import com.study.toDoList.schedule.dto.ScheduleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ScheduleController {
    private final AuthService authService;
    private final ScheduleService scheduleService;
    private final UserRepository userRepository;

    @GetMapping("/api/my")
    public ScheduleResponseDto sendMyScheduleInfo(HttpServletRequest request){
        User user=authService.getMemberInfo(request);
        return scheduleService.getMySchedules(user);
        //return scheduleService.getMySchedules(id);
    }
    @GetMapping("/api/my/{id}")
    public ScheduleResponseDto sendMyScheduleInfoT(@PathVariable("id")Long id, HttpServletRequest request){
        User user=userRepository.findById(id).orElseThrow(()->new IllegalArgumentException());
        return scheduleService.getMySchedules(user);
    }

}

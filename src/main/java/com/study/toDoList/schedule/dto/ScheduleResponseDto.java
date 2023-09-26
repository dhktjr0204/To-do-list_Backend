package com.study.toDoList.schedule.dto;

import com.study.toDoList.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ScheduleResponseDto {
    private User user;
    private List<Long> closeDeadline;
    private List<ScheduleResponseVo> scheduleList;

    @Builder
    public ScheduleResponseDto(User user, List<Long> closeDeadline, List<ScheduleResponseVo> scheduleList) {
        this.user = user;
        this.closeDeadline = closeDeadline;
        this.scheduleList = scheduleList;
    }
}

package com.study.toDoList.manageSchedule.dto;

import com.study.toDoList.domain.Content;
import com.study.toDoList.domain.Schedule;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class CreateResponseDto {
    Schedule schedule;
    List<Content> contents;

    @Builder
    public CreateResponseDto(Schedule schedule,List<Content> contents){
        this.schedule=schedule;
        this.contents=contents;
    }
}

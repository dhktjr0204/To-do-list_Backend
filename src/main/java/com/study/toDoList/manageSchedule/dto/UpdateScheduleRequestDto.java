package com.study.toDoList.manageSchedule.dto;

import com.study.toDoList.domain.Content;
import lombok.Getter;

import java.util.List;

@Getter
public class UpdateScheduleRequestDto {
    String type;
    String day;
    String closingDate;
    List<Content> contents;
}

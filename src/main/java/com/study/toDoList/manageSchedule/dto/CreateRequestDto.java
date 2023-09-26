package com.study.toDoList.manageSchedule.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class CreateRequestDto {
    private String email;
    private List<String> contents;
    private String type;
    private String day;
    private String closingDate;
    @Builder
    public CreateRequestDto(String email, List<String> contents, String type, String day, String closingDate) {
        this.email = email;
        this.contents = contents;
        this.type = type;
        this.day = day;
        this.closingDate = closingDate;
    }
}

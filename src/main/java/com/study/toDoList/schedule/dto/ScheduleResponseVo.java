package com.study.toDoList.schedule.dto;

import com.study.toDoList.domain.Content;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class ScheduleResponseVo {
    private Long id;
    private String type;
    private LocalDate closingDate;
    private List<Content> content;
    Character allcomplete;

    @Builder
    public ScheduleResponseVo(Long id , String type, LocalDate closingDate, List<Content> content, Character allcomplete){
        this.id=id;
        this.allcomplete=allcomplete;
        this.type=type;
        this.closingDate=closingDate;
        this.content=content;
    }

}

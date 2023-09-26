package com.study.toDoList.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private Character status;
    private Long schedule_id;
    @Builder
    public Content(Long id, String content, Character status, Long schedule_id){
        this.id=id;
        this.content=content;
        this.status=status;
        this.schedule_id=schedule_id;
    }

    public void update(Character status){
        this.status=status;
    }
}

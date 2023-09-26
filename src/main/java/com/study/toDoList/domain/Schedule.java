package com.study.toDoList.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //fetch=LAZY로 해놓으면 N+1현상이 일어나지 않는다
    //안해놓으면 left outer join으로 user와 team도 함께 조회하며, user0_.userid와 user0_.team_id를 다 참조하게된다
    @ManyToOne
    private User user;
    private String type;
    private String day;
    private LocalDate closingDate;
    private Character complete;

    @Builder
    public Schedule(Long id, User user, String type,String day, LocalDate closingDate, Character complete){
        this.id=id;
        this.user=user;
        this.type=type;
        this.day=day;
        this.closingDate=closingDate;
        this.complete=complete;
    }

    public void update(Character complete){
        this.complete=complete;
    }
    public void update(String type, String day, LocalDate closingDate){
        this.type=type;
        this.day=day;
        this.closingDate=closingDate;
    }
}

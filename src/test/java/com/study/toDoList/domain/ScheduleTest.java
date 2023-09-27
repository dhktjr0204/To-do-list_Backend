package com.study.toDoList.domain;

import com.study.toDoList.domain.enums.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ScheduleTest {
    @Test
    @DisplayName("schedule 잘 생성되는지 확인")
    void createSchedule(){
        //given
        User user = User.builder().name("test").email("test@gmail.com").role(Role.USER).build();
        Schedule schedule = Schedule.builder().user(user).type("deadline").day(null).closingDate(LocalDate.now()).complete('N').build();
        //when,then
        assertThat(schedule.getUser()).isEqualTo(user);
        assertThat(schedule.getComplete()).isEqualTo('N');
        assertThat(schedule.getClosingDate()).isEqualTo(LocalDate.now());
    }

    @Test
    @DisplayName("complete 업데이트 확인")
    void updateSchedule(){
        //given
        User user = User.builder().name("test").email("test@gmail.com").role(Role.USER).build();
        Schedule schedule = Schedule.builder().user(user).type("deadline").day(null).closingDate(LocalDate.now()).complete('N').build();
        //when
        schedule.update('Y');
        // then
        assertThat(schedule.getUser()).isEqualTo(user);
        assertThat(schedule.getComplete()).isEqualTo('Y');
        assertThat(schedule.getClosingDate()).isEqualTo(LocalDate.now());
    }

    @Test
    @DisplayName("전체 업데이트 확인")
    void updateAllSchedule(){
        //given
        User user = User.builder().name("test").email("test@gmail.com").role(Role.USER).build();
        Schedule schedule = Schedule.builder().user(user).type("deadline").day(null).closingDate(LocalDate.now()).complete('N').build();
        //when
        schedule.update("day",null,null);
        // then
        assertThat(schedule.getDay()).isEqualTo(null);
        assertThat(schedule.getType()).isEqualTo("day");
        assertThat(schedule.getClosingDate()).isNull();
    }
}
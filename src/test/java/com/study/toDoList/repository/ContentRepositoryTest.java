package com.study.toDoList.repository;

import com.study.toDoList.domain.Content;
import com.study.toDoList.domain.Schedule;
import com.study.toDoList.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
//테스트 데이터베이스를 사용하지 않고 실제 데이터베이스를 사용하도록 한다.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ContentRepositoryTest {
    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp(){
        User user = User.builder().name("test").email("test@gmail.com").build();
        userRepository.save(user);
        Schedule schedule = Schedule.builder().user(user).type("day").day(null).closingDate(null).complete('N').build();
        Long scedule_id = scheduleRepository.save(schedule).getId();
        Content content = Content.builder().content("공부하기").status('N').schedule_id(scedule_id).build();
        contentRepository.save(content);
    }

    @AfterEach
    public void tearDown(){
        User user = userRepository.findByEmail("test@gmail.com").orElseThrow(() -> new IllegalArgumentException());
        userRepository.delete(user);
    }
    @Test
    public void testFindByScheduleId(){
        //given
        User user = userRepository.findByEmail("test@gmail.com").orElseThrow(() -> new IllegalArgumentException());
        List<Schedule> schedules = scheduleRepository.findByUserId(user.getId());
        //when
        List<Content> contents = contentRepository.findByScheduleId(schedules.get(0).getId());
        //then
        assertThat(contents).hasSize(1);
    }

}
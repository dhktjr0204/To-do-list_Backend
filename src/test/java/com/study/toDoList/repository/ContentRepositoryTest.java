package com.study.toDoList.repository;

import com.study.toDoList.domain.Content;
import com.study.toDoList.domain.Schedule;
import com.study.toDoList.domain.User;
import org.assertj.core.api.ListAssert;
import org.junit.jupiter.api.*;
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
    @DisplayName("저장 후 스케줄이 잘 뽑아지는 지 확인")
    public void testFindByScheduleId(){
        //given
        User user = userRepository.findByEmail("test@gmail.com").orElseThrow(() -> new IllegalArgumentException());
        List<Schedule> schedules = scheduleRepository.findByUserId(user.getId());
        List<Content> contents = contentRepository.findByScheduleId(schedules.get(0).getId());
        //when, then
        assertThat(contents).hasSize(1);
        assertThat(schedules).hasSize(1);
    }

    @Test
    @DisplayName("status 업데이트")
    public void testUpdate(){
        //given
        User user = userRepository.findByEmail("test@gmail.com").orElseThrow(() -> new IllegalArgumentException());
        List<Schedule> schedules = scheduleRepository.findByUserId(user.getId());
        List<Content> contents = contentRepository.findByScheduleId(schedules.get(0).getId());
        //when
        for (Content content : contents) {
            content.update('Y');
        }
        for (Schedule schedule : schedules) {
            schedule.update('Y');
        }
        //then
        assertThat(schedules.get(0).getComplete()).isEqualTo('Y');
        assertThat(contents.get(0).getStatus()).isEqualTo('Y');
    }

}
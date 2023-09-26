package com.study.toDoList.repository;

import com.study.toDoList.domain.Schedule;
import com.study.toDoList.domain.ScheduleInterface;
import com.study.toDoList.domain.User;
import org.junit.jupiter.api.AfterEach;
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
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ScheduleRepositoryTest {
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private UserRepository userRepository;
    private Schedule createSchedule(User user, String type, LocalDate date) {
        return Schedule.builder()
                .user(user)
                .type(type)
                .day(null)
                .closingDate(date)
                .complete('N')
                .build();
    }

    @BeforeEach
    public void setUp(){
        User user = User.builder().name("test").email("test@gmail.com").build();
        userRepository.save(user);
        LocalDate date1 = LocalDate.now();
        LocalDate date2 = LocalDate.of(2023, 8, 25);
        LocalDate date3 = LocalDate.of(2023, 11, 30);
        Schedule schedule1 = createSchedule(user,"deadline",date1);
        Schedule schedule2 = createSchedule(user,"deadline",date2);
        Schedule schedule3 = createSchedule(user,"deadline",date3);
        scheduleRepository.saveAll(Arrays.asList(schedule1,schedule2,schedule3));
    }

    @AfterEach
    public void tearDown(){
        User user = userRepository.findByEmail("test@gmail.com").orElseThrow(() -> new IllegalArgumentException());
        userRepository.delete(user);
    }

    @Test
    public void testFindByDeadLine(){
        //given
        User user = userRepository.findByEmail("test@gmail.com").orElseThrow(() -> new IllegalArgumentException());
        //when
        List<ScheduleInterface> byDeadLine = scheduleRepository.findByDeadLine(user.getId());
        //then
        assertThat(byDeadLine).hasSize(1);
    }

}
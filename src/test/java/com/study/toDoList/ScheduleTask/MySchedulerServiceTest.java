package com.study.toDoList.ScheduleTask;

import com.study.toDoList.domain.Content;
import com.study.toDoList.domain.Schedule;
import com.study.toDoList.domain.User;
import com.study.toDoList.domain.enums.Role;
import com.study.toDoList.repository.ContentRepository;
import com.study.toDoList.repository.ScheduleRepository;
import com.study.toDoList.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//스케줄링 활성화
@TestPropertySource(properties = {"spring.scheduling.enabled=true"})
class MySchedulerServiceTest {

    @Autowired
    private MySchedulerService mySchedulerService;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private UserRepository userRepository;

    private Schedule createSchedule(User user, String type, String day) {
        return Schedule.builder()
                .user(user)
                .type(type)
                .day(day)
                .closingDate(null)
                .complete('Y')
                .build();
    }

    private Content createContent(String content, Character status, Long scheduleId) {
        return Content.builder()
                .content(content)
                .status(status)
                .schedule_id(scheduleId)
                .build();
    }

    @BeforeEach
    public void setUp(){
        // 유저 생성
        User user = User.builder().name("테스트").email("test@gmail.com").role(Role.USER).build();
        userRepository.save(user);

        // 스케줄 생성
        Schedule schedule1 = createSchedule(user, "day", null);
        Schedule schedule2 = createSchedule(user, "week", "wed");
        Long schedule_id1 = scheduleRepository.save(schedule1).getId();
        Long schedule_id2 = scheduleRepository.save(schedule2).getId();

        // 컨텐트 생성
        Content content1 = createContent("테스트1", 'Y', schedule_id1);
        Content content1_2 = createContent("테스트2", 'Y', schedule_id1);

        Content content2 = createContent("테스트3", 'Y', schedule_id2);
        Content content2_2 = createContent("테스트4", 'Y', schedule_id2);

        contentRepository.saveAll(Arrays.asList(content1, content1_2, content2, content2_2));
    }

    @AfterEach
    public void tearDown(){
        User user = userRepository.findByEmail("test@gmail.com").orElseThrow(() -> new IllegalArgumentException());
        userRepository.delete(user);
    }

    //스케줄러 작업이 스레드 풀에서 실행될 때까지 기다리도록 설정
    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    @Test
    public void 매일초기화되는지확인() throws InterruptedException{
        //given
        //하나의 작업이 완료될 때까지 대기하겠다.
        CountDownLatch latch=new CountDownLatch(1);
        //스케줄러 작업실행
        mySchedulerService.DayScheduledTask();
        //스케줄러 작업을 실행하기 위해 스레드 풀에서 작업이 처리되기를 기다린다.
        latch.await(10, TimeUnit.SECONDS);
        User user = userRepository.findByEmail("test@gmail.com").orElseThrow(() -> new IllegalArgumentException());
        //when
        List<Schedule> schedules = scheduleRepository.findByUserId(user.getId());
        List<Content> contents = contentRepository.findByScheduleId(schedules.get(0).getId());
        //then
        assertThat(contents).allMatch(content -> content.getStatus()=='N');
    }

    @Test
    public void 정해진요일마다초기화되는지확인() throws InterruptedException{
        //given
        CountDownLatch latch=new CountDownLatch(1);

        mySchedulerService.WednesDayScheduledTask();

        latch.await(10,TimeUnit.SECONDS);
        //when
        User user = userRepository.findByEmail("test@gmail.com").orElseThrow(() -> new IllegalArgumentException());
        List<Schedule> schedules = scheduleRepository.findByUserId(user.getId());
        List<Content> contents = contentRepository.findByScheduleId(schedules.get(1).getId());
        //then
        assertThat(contents).allMatch(content -> content.getStatus()=='N');
    }
}
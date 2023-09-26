package com.study.toDoList.ScheduleTask;

import com.study.toDoList.domain.Content;
import com.study.toDoList.domain.Schedule;
import com.study.toDoList.repository.ContentRepository;
import com.study.toDoList.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MySchedulerService {
    private final ScheduleRepository scheduleRepository;
    private final ContentRepository contentRepository;
    @Transactional
    @Scheduled(cron = "0 0 0 * * ?")//매일
    public void DayScheduledTask(){
        List<Schedule> schedules = scheduleRepository.findByType("day");
        updateSchedule(schedules);
    }
    @Transactional
    @Scheduled(cron = "0 0 0 * * MON") // 월요일
    public void MondayScheduledTask() {
        List<Schedule> schedules = scheduleRepository.findByDay("mon");
        updateSchedule(schedules);
    }
    @Transactional
    @Scheduled(cron = "0 0 0 * * TUE") // 화요일
    public void TuesDayScheduledTask() {
        List<Schedule> schedules = scheduleRepository.findByDay("tue");
        updateSchedule(schedules);
    }
    @Transactional
    @Scheduled(cron = "0 0 0 * * WED") // 수요일
    public void WednesDayScheduledTask() {
        List<Schedule> schedules = scheduleRepository.findByDay("wed");
        updateSchedule(schedules);
    }
    @Transactional
    @Scheduled(cron = "0 0 0 * * THU") // 목요일
    public void ThursDayScheduledTask() {
        List<Schedule> schedules = scheduleRepository.findByDay("thu");
        updateSchedule(schedules);
    }
    @Transactional
    @Scheduled(cron = "0 0 0 * * FRI") // 금요일
    public void FriDayScheduledTask() {
        List<Schedule> schedules = scheduleRepository.findByDay("fri");
        updateSchedule(schedules);
    }
    @Transactional
    @Scheduled(cron = "0 0 0 * * SAT") // 토요일
    public void SaturdayScheduledTask() {
        List<Schedule> schedules = scheduleRepository.findByDay("sat");
        updateSchedule(schedules);
    }
    @Transactional
    @Scheduled(cron = "0 0 0 * * SUN") // 일요일
    public void SundayScheduledTask() {
        List<Schedule> schedules = scheduleRepository.findByDay("sun");
        updateSchedule(schedules);
    }
    public void updateSchedule(List<Schedule> schedules){
        for (Schedule schedule: schedules){
            List<Content> contents = contentRepository.findByScheduleId(schedule.getId());
            for (Content content: contents){
                content.update('N');
            }
            schedule.update('N');
        }
    }
}

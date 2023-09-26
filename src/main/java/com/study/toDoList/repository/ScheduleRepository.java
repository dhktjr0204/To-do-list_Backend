package com.study.toDoList.repository;

import com.study.toDoList.domain.Schedule;
import com.study.toDoList.domain.ScheduleInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByUserId(Long id);
    List<Schedule> findByType(String type);
    List<Schedule> findByDay(String day);
    @Query(value = "SELECT id FROM schedule"
            +" WHERE closing_date>=CURDATE()"
            +" AND closing_date<=DATE_ADD(CURDATE(), INTERVAL 7 DAY)"+
            " AND complete='N' AND user_id=:id",nativeQuery = true)
    List<ScheduleInterface> findByDeadLine(@Param("id") Long id);
}

package com.study.toDoList.repository;

import com.study.toDoList.domain.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContentRepository extends JpaRepository<Content, String> {
    Optional<Content> findById(Long id);
    @Query(value = "SELECT * FROM content WHERE schedule_id=:id",nativeQuery = true)
    List<Content> findByScheduleId(@Param("id") Long id);
}

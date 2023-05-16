package com.codestates.edusync.study.plancalendar.studygroup.repository;

import com.codestates.edusync.infodto.timeschedule.entity.TimeSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalendarStudygroupRepository extends JpaRepository<TimeSchedule, Long> {

    List<TimeSchedule> findAllByStudygroupId(Long studygroupId);
}
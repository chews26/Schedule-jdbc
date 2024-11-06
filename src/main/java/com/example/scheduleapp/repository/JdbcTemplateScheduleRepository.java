package com.example.scheduleapp.repository;

import com.example.scheduleapp.dto.ScheduleResponseDto;
import com.example.scheduleapp.entity.Schedule;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository {

    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {
        return null;
    }
}

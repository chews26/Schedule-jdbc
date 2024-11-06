package com.example.scheduleapp.service;

import com.example.scheduleapp.dto.ScheduleRequestDto;
import com.example.scheduleapp.dto.ScheduleResponseDto;

public interface ScheduleService {

    // 일정 저장
    ScheduleResponseDto saveSchedule(ScheduleRequestDto dto);

}

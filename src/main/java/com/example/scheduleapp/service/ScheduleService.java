package com.example.scheduleapp.service;

import com.example.scheduleapp.dto.ScheduleRequestDto;
import com.example.scheduleapp.dto.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {

    // 일정 저장
    ScheduleResponseDto saveSchedule(ScheduleRequestDto dto);

    // 일정 전체 조회
    List<ScheduleResponseDto> findAllSchedules();

    // 일정 세부 조회
    ScheduleResponseDto findScheduleById(Long id);

    // TODO : 일정 변경
    ScheduleResponseDto updateSchedule(ScheduleRequestDto dto);

    // TODO : 일정 삭제
    void deleteSchedule(String id);

}

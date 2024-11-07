package com.example.scheduleapp.repository;

import com.example.scheduleapp.dto.ScheduleResponseDto;
import com.example.scheduleapp.entity.Schedule;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository {

    // 일정 저장
    ScheduleResponseDto saveSchedule(Schedule schedule);

    // 일정 전체 조회
    List<ScheduleResponseDto> findAllSchedules();

    // TODO : 일정 세부 조회
    Schedule findScheduleByIdOrElseThrow(long id);

    // TODO : 일정 수정
    int updateSchedule(Long id, String title, String name, LocalDateTime startDate, LocalDateTime endDate, String description);

    // TODO : 일정 삭제
    int deleteSchedule(Long id);
}

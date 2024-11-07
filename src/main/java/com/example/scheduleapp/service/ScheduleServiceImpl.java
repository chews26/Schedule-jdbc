package com.example.scheduleapp.service;

import com.example.scheduleapp.dto.ScheduleRequestDto;
import com.example.scheduleapp.dto.ScheduleResponseDto;
import com.example.scheduleapp.entity.Schedule;
import com.example.scheduleapp.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.beans.Transient;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    // 일정 저장
    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto) {

        Schedule schedule = new Schedule(requestDto.getTitle(), requestDto.getName(), requestDto.getPassword(), requestDto.getStartDateTime(), requestDto.getEndDateTime(), requestDto.getDescription());

        return scheduleRepository.saveSchedule(schedule);
    }

    // 일정 전체 조회
    @Override
    public List<ScheduleResponseDto> findAllSchedules() {
        return scheduleRepository.findAllSchedules();
    }

    // 일정 세부 조회
    @Override
    public ScheduleResponseDto findScheduleById(Long id) {
        Schedule schedule = scheduleRepository.findScheduleByIdOrElseThrow(id);
        return new ScheduleResponseDto(schedule);
    }

    // 일정 수정
    @Transactional
    @Override
    public ScheduleResponseDto updateSchedule(Long id, String title, String name, String password, LocalDateTime startDateTime, LocalDateTime endDateTime, String description) {
        LocalDateTime revisionDate = LocalDateTime.now();

        Schedule schedule = scheduleRepository.findScheduleByIdOrElseThrow(id);

        if(!schedule.getName().equals(name) || !schedule.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "invalid name Or password");
        }

        if (title == null || name == null || password == null|| startDateTime == null || endDateTime == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "required values");
        }

        int updateRow = scheduleRepository.updateSchedule(id, title, name, revisionDate, startDateTime, endDateTime, description);

        if (updateRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found");
        }
        Schedule updatedSchedule = scheduleRepository.findScheduleByIdOrElseThrow(id);
        return new ScheduleResponseDto(updatedSchedule);
    }

    // 일정 삭제
    @Override
    public void deleteSchedule(String id) {

    }
}

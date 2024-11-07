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
        // 수정할 경우 수정일자 현재 시각으로 재설정
        LocalDateTime revisionDate = LocalDateTime.now();

        // 데이터베이스 id 값을 가져옴
        Schedule schedule = scheduleRepository.findScheduleByIdOrElseThrow(id);

        // 이름이랑 비밀번호 불일치시 예외 반환
        if(!schedule.getName().equals(name) || !schedule.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "invalid name Or password");
        }

        // null 값을 경우 예외 반환
        if (title == null || name == null || password == null|| startDateTime == null || endDateTime == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "required values");
        }

        // 수정 된 값 업데이트
        int updateRow = scheduleRepository.updateSchedule(id, title, name, revisionDate, startDateTime, endDateTime, description);

        // 데이터베이스에서 일정 업데이트 작업이 제대로 수행되지 않을 경우 예외 반환
        if (updateRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found");
        }

        // 최신데이터 재조회
        // <<트러블슈팅 >>
        // 문제 : 값을 수정해도 응답값에 최신값이 응답되지 않는 문제가 발생
        // 해결 방법 : 최신 데이터 재조회로 해결, 최신데이터 재조회를 안할경우 200.Ok가 되나 갱신되지 않은 JSON값을 반환합니다.
        Schedule updatedSchedule = scheduleRepository.findScheduleByIdOrElseThrow(id);
        return new ScheduleResponseDto(updatedSchedule);
    }

    // 일정 삭제
    @Override
    public void deleteSchedule(Long id) {
        int deleteRow = scheduleRepository.deleteSchedule(id);

        if (deleteRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found id = " + id);
        }
    }
}

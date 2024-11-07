package com.example.scheduleapp.controller;

import com.example.scheduleapp.dto.ScheduleRequestDto;
import com.example.scheduleapp.dto.ScheduleResponseDto;
import com.example.scheduleapp.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    /*
    * 생성자 주입
    * 생성자 주입은 객체 지향 프로그래밍에서 의존성 주입(Dependency Injection)의 한 방법으로,
    * 클래스가 필요로 하는 의존성(다른 객체들)을 생성자를 통해 주입받는 방식
    * */
    private final ScheduleService scheduleService;
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    // 일정 게시 컨트롤러

    @PostMapping // HTTP POST 요청 처리 메서드
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto dto) {
        return new ResponseEntity<>(scheduleService.saveSchedule(dto), HttpStatus.CREATED);
    }

    // 일정 전체 조회 컨트롤러
    @GetMapping
    public List<ScheduleResponseDto> findAllSchedules() {
        return scheduleService.findAllSchedules();
    }

    // 일정 세부 조회
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable Long id) {
        return new ResponseEntity<>(scheduleService.findScheduleById(id), HttpStatus.OK);
    }

    // 일정 수정
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable Long id, @RequestBody ScheduleRequestDto dto
    ) {
        return new ResponseEntity<>(scheduleService.updateSchedule(id, dto.getTitle(), dto.getName(), dto.getPassword(), dto.getStartDateTime(), dto.getEndDateTime(), dto.getDescription()), HttpStatus.OK);
    }

    // 일정 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}


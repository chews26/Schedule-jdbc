package com.example.scheduleapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleRequestDto {

    private String title;
    private String name; // day2 추가
    private String password;  //  day2, 미구현 내용 추가 구현
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDateTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDateTime;
    private String description;

    public ScheduleRequestDto(String name, String password) {
        this.name = name;
        this.password = password;
    }
}


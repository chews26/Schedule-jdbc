package com.example.scheduleapp.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleRequestDto {

    private String title;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String description;

}


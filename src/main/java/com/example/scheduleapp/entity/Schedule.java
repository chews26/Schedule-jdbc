package com.example.scheduleapp.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Schedule {

    private Long id;
    private String title;
    private String name; // day2 추가 작성자
    private LocalDateTime creationDate; // day2 추가 생성일자
    private LocalDateTime revisionDate; // day2 추가 수정일자
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String description;

    public Schedule(String title, String name, LocalDateTime startDateTime, LocalDateTime endDateTime, String description) {
        this.title = title;
        this.name = name;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.description = description;
        this.creationDate = LocalDateTime.now(); // 현재 시간으로 설정
    }
}

package com.example.scheduleapp.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Schedule {

    @Setter
    private Long id;
    private String title;
    private String name;
    private String password;// day2 추가 작성자

    private LocalDateTime creationDate; // day2 추가 생성일자
    private LocalDateTime revisionDate; // day2 추가 수정일자
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String description;


    public Schedule(String title, String name, String password, LocalDateTime startDateTime, LocalDateTime endDateTime, String description) {
        this.title = title;
        this.name = name;
        this.password = password;
        this.creationDate = LocalDateTime.now(); // 현재 시간으로 설정
        this.revisionDate = creationDate;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.description = description;
    }

    public Schedule(long id, String title, String name, LocalDateTime creationDate, LocalDateTime revisionDate, LocalDateTime startDatetime, LocalDateTime endDatetime, String description) {
        this.id = id;
        this.title = title;
        this.name = name;
        this.creationDate = creationDate;
        this.revisionDate = revisionDate;
        this.startDateTime = startDatetime;
        this.endDateTime = endDatetime;
        this.description = description;
    }
}

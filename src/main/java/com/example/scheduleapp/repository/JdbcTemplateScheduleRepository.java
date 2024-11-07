package com.example.scheduleapp.repository;

import com.example.scheduleapp.dto.ScheduleResponseDto;
import com.example.scheduleapp.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateScheduleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {

        // INSERT Query 직접 작성하지 않아도 된다.
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", schedule.getTitle());
        parameters.put("name", schedule.getName());
        parameters.put("creation_date", Timestamp.valueOf(schedule.getCreationDate())); // creation_date 추가
        parameters.put("start_datetime", Timestamp.valueOf(schedule.getStartDateTime()));
        parameters.put("end_datetime", Timestamp.valueOf(schedule.getEndDateTime()));
        parameters.put("description", schedule.getDescription());


        // jdbcInsert.executeAndReturnKey는 SimpleJdbcInsert의 메서드로, 데이터베이스에 데이터를 삽입하면서 자동으로 생성된 기본 키(Primary Key) 값을 반환하는 역할
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        // Schedule 객체의 id 필드에 자동 생성된 기본 키 값을 설정하는 코드
        // key.longValue() 호출: Number 타입으로 반환된 기본 키 값은 longValue() 메서드를 통해 Long 타입으로 변환
        // schedule.setId(key.longValue());는 Schedule 객체의 id 필드에 key.longValue() 값을 설정하여, Schedule 객체가 데이터베이스에 저장된 레코드의 기본 키 값을 가지게 함
        schedule.setId(key.longValue());

        return new ScheduleResponseDto(schedule);

    }
}

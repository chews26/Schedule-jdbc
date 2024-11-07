package com.example.scheduleapp.repository;

import com.example.scheduleapp.dto.ScheduleResponseDto;
import com.example.scheduleapp.entity.Schedule;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateScheduleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 일정 등록
    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {

        // INSERT Query 직접 작성하지 않아도 된다.
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("id");
        LocalDateTime revisionDate = LocalDateTime.now();

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", schedule.getTitle());
        parameters.put("name", schedule.getName());
        parameters.put("password", schedule.getPassword()); // password 추가..
        parameters.put("creation_date", Timestamp.valueOf(schedule.getCreationDate()));// creation_date 추가
        parameters.put("revision_date", Timestamp.valueOf(revisionDate));
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

    // 일정 전체 조회
    @Override
    public List<ScheduleResponseDto> findAllSchedules() {
        return jdbcTemplate.query("SELECT id, title, name, creation_date, revision_date, start_datetime, end_datetime, description FROM schedule", scheduleRowMapper());
    }

    // 일정 세부 조회
    @Override
    public Schedule findScheduleByIdOrElseThrow(long id) {
        List<Schedule> result = jdbcTemplate.query("select * from schedule where id=?", scheduleRowMapperV2(), id);
        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }

    // 일정 수정
    @Override
    public int updateSchedule(Long id, String title, String name, LocalDateTime revisionDate, LocalDateTime startDate, LocalDateTime endDate, String description) {
        return jdbcTemplate.update("update schedule set title=?, name=?, revision_date=?, start_datetime=?, end_datetime=?, description=? where id=?", title, name, revisionDate, startDate, endDate, description, id);
    }

    // 일정 삭제
    @Override
    public int deleteSchedule(Long id) {
        return 0;
    }

    // RowMapper는 JDBC에서 사용되는 인터페이스
    // 데이터베이스에서 조회된 ResultSet의 각 행을 원하는 객체 타입으로 매핑할 때 사용
    private RowMapper<ScheduleResponseDto> scheduleRowMapper() {
        return new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ScheduleResponseDto(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("name"),
                        rs.getTimestamp("creation_date").toLocalDateTime(),
                        rs.getTimestamp("revision_date").toLocalDateTime(),
                        rs.getTimestamp("start_datetime").toLocalDateTime(),
                        rs.getTimestamp("end_datetime").toLocalDateTime(),
                        rs.getString("description")
                );
            }
        };
    }
    private RowMapper<Schedule> scheduleRowMapperV2() {
        return new RowMapper<Schedule>() {
            @Override
            public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Schedule(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getTimestamp("creation_date").toLocalDateTime(),
                        rs.getTimestamp("revision_date").toLocalDateTime(),
                        rs.getTimestamp("start_datetime").toLocalDateTime(),
                        rs.getTimestamp("end_datetime").toLocalDateTime(),
                        rs.getString("description")
                );
            }
        };
    }

}

# 📆 일정관리 앱 📆
### [📓 일정관리 앱 만들기 작성 노션 바로가기](https://shinelee26.notion.site/_-12e86ea33f94807cb131f8bcd0feb95d?pvs=4)
### [🏠 일정관리 앱 만들기 작성 블로그 바로가기](https://shinelee26.tistory.com/tag/spring%20%EC%9D%BC%EC%A0%95%EA%B4%80%EB%A6%AC%EC%95%B1)

## API 명세서
| 단계       | 기능       | HTTP 메서드 | 설명         | 엔드포인트                     | 요청 본문                                                                                     | 응답 본문                                                                  | 응답 코드 및 메시지                                        |
|------------|------------|-------------|--------------|--------------------------------|------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------|-----------------------------------------------------------|
| 시작 전    | 회원관리   | POST        | 회원가입     | /users                         | { "name": "shine", "id": "user123", "password": "q1w2e3r4" }                               | { "message": "회원가입이 완료되었습니다" }                    | 200: SUCCESS(정상처리), 400: CONFLICT(충돌)               |
| 시작 전    | 회원관리   | POST        | 로그인       | /users/login                   | { "id": "user123", "password": "q1w2e3r4" }                                                 | { "message": "shine님 환영합니다." }                        | 200: SUCCESS(정상처리), 400: BAD_REQUEST_DATA(잘못된 요청) |
| 시작 전    | 일정관리   | POST        | 일정 등록    | /users/{userId}/event          | { "title": "공부하기", "start_datetime": "2024-10-30 16:00:00", "end_datetime": "2024-10-31 17:00:00", "description": "zep에 접속하기" } | { "message": "일정이 등록되었습니다.", "eventId": "123" }     | 200: SUCCESS(정상처리), 400: BAD_REQUEST_DATA(잘못된 요청), 401: UNAUTHORIZED(권한없음) |
| 시작 전    | 일정관리   | GET         | 일정 전체 조회 | /users/{userId}/events?month   | { "month": "10" }                                                                           | { "event": [ { "eventId": "123", "name": "shine", "title": "공부하기", "start_datetime": "2024-10-30 16:00:00", "end_datetime": "2024-10-31 17:00:00" }, { "eventId": "124", "name": "shine", "title": "놀기", "start_datetime": "2024-10-30 14:00:00", "end_datetime": "2024-10-31 18:00:00" } ] } | 200: SUCCESS(정상처리), 400: BAD_REQUEST_DATA(잘못된 요청), 401: UNAUTHORIZED(권한없음), 404: NOT_FOUND(찾을 수 없음) |
| 시작 전    | 일정관리   | GET         | 세부 일정 조회 | /users/{userId}/event/{eventId} | { "eventId": "123" }                                                                         | { "name": "shine", "title": "공부하기", "start_datetime": "2024-10-30 16:00:00", "end_datetime": "2024-10-31 17:00:00", "description": "zep에 접속하기" } | 200: SUCCESS(정상처리), 401: UNAUTHORIZED(권한없음), 404: NOT_FOUND(찾을 수 없음) |
| 시작 전    | 일정관리   | PUT         | 일정 수정    | /users/{userId}/event/{eventId} | { "title": "친구만나기", "start_datetime": "2024-10-30 16:00:00", "end_datetime": "2024-10-31 17:00:00", "description": "친구만나서 밥먹기" } | { "message": "일정이 수정되었습니다." }                        | 200: SUCCESS(정상처리), 400: BAD_REQUEST_DATA(잘못된 요청), 401: UNAUTHORIZED(권한없음) |
| 시작 전    | 일정관리   | DELETE      | 일정 삭제    | /users/{userId}/event/{eventId} | { "eventId": "123" }                                                                         | { "message": "일정이 삭제되었습니다." }                        | 200: SUCCESS(정상처리), 401: UNAUTHORIZED(권한없음)        |

## ERD 다이어그램
#### [ERD Cloud - 일정관리 앱 작성내용 바로가기](https://www.erdcloud.com/d/zDQNGkHLaqenumNhz)
<p align="center">
<img src="https://github.com/user-attachments/assets/e700317a-ddf8-45be-baa8-2bd30e7781dc" width="100%" height="100%">
</p>

## SQL 쿼리 작성 (schedule.sql)
### Create
- 필수 기능 가이드 개발에 필요한 테이블을 생성하는 query를 작성
```sql
CREATE TABLE `users` (
	`user_id` INTEGER NOT NULL,
	`user_name` VARCHAR NOT NULL,
	`user_password` VARCHAR	NOT NULL,
	`create_date` DATETIME NOT NULL
	PRIMARY KEY (user_id)
);

CREATE TABLE `event` (
	`event_id` INTEGER NOT NULL,
	`user_id` INTEGER NOT NULL,
	`title`	VARCHAR(100) NOT NULL,
	`start_datetime` DATETIME NOT NULL,
	`end_datetime` DATETIME NOT NULL,
	`description` VARCHAR(100) NULL
	PRIMARY KEY (event_id)
	FOREIGN KEY (user_id) REFERENCES users(user_id)
);
```
###  Insert
- 일정 생성을 하는 query를 작성
```sql
INSERT INTO event
(
title,
start_datetime,
end_datetime,
description
)
VALUE(
'공부하기'
'2024-10-30 16:00:00'
'2024-10-31 17:00:00'
'zep에 접속하기'
)
;
```
### Select
- 전체 일정을 조회하는 query를 작성
```sql
SELECT *
FROM event
ORDER BY start_datetime DESC;
```
### Update
- 선택한 일정을 수정하는 query를 작성
```sql
UPDATE event
SET title '놀기',
start_datetime = '2024-11-31 09:00:00'
end_datetime = '2024-11-31 11:00:00'
description = '게임하기'
WHERE eventid=123;
```
### Delete
- 선택한 일정을 삭제하는 query를 작성
```sql
DELETE FROM event
WHERE eventid=124;
```




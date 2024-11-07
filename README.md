# 📆 일정관리 앱 📆
### [💻 일정관리 앱 만들기 작성 내용 바로가기 ](https://github.com/chews26/Schedule-app/tree/main/src/main/java/com/example/scheduleapp)
### [📓 일정관리 앱 만들기 작성 노션 바로가기](https://shinelee26.notion.site/_-12e86ea33f94807cb131f8bcd0feb95d?pvs=4)
### [🏠 일정관리 앱 만들기 작성 블로그 바로가기](https://shinelee26.tistory.com/tag/spring%20%EC%9D%BC%EC%A0%95%EA%B4%80%EB%A6%AC%EC%95%B1)

## API 명세서
![스크린샷 2024-11-07 205815](https://github.com/user-attachments/assets/2e8f02e9-440e-45c0-9038-f301fa188b1c)


## ERD 다이어그램
#### [ERD Cloud - 일정관리 앱 작성내용 바로가기](https://www.erdcloud.com/d/zDQNGkHLaqenumNhz)
![스크린샷 2024-11-07 205918](https://github.com/user-attachments/assets/71668e78-94e1-4720-a045-75e052f7f42d)

## SQL 쿼리 작성 (schedule.sql)
### Create
- 필수 기능 가이드 개발에 필요한 테이블을 생성하는 query를 작성
```sql
create table schedule
(
    id             bigint auto_increment comment '일정 id'
        primary key,
    title          varchar(100) not null comment '제목',
    name           varchar(100) null,
    password       varchar(10)  not null comment '비밀번호',
    creation_date  timestamp    not null comment '작성일',
    revision_date  timestamp    null comment '수정일',
    start_datetime datetime     not null comment '시작 일자',
    end_datetime   datetime     null comment '종료 일자',
    description    text         null comment '일정 설명'
);
```
###  Insert
- 일정 생성을 하는 query를 작성
```sql
INSERT INTO schedule
(
title,
name,
password,
startDateTime,
endDateTime,
description
)
VALUE(
'공부하기'
'곽두팔'
'4885'
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
FROM schedule;
```
### Update
- 선택한 일정을 수정하는 query를 작성
```sql
UPDATE schedule
SET title = '놀기',
name = '곽두팔'
password = '4885'
start_datetime = '2024-11-31 09:00:00'
end_datetime = '2024-11-31 11:00:00'
description = '게임하기'
WHERE id=123;
```
### Delete
- 선택한 일정을 삭제하는 query를 작성
```sql
DELETE FROM schedule
WHERE id=124;
```




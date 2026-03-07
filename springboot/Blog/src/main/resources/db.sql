CREATE DATABASE IF NOT EXISTS blog;
use blog;

drop table blog;
drop table blogUserInfo;


create table blog(
	id bigint AUTO_INCREMENT PRIMARY KEY,
	user_title varchar(255) not null,
	user_content varchar(255) not null,
	 date_of_write DATE DEFAULT (CURRENT_DATE)
);
select *from blog;

CREATE TABLE blogUserInfo (
    id BIGINT AUTO_INCREMENT PRIMARY KEY, -- 고유 번호 (PK)
    user_id VARCHAR(255) NOT NULL UNIQUE,  -- 로그인 아이디 (중복 불가!)
    user_password VARCHAR(255) NOT NULL,
    user_email VARCHAR(255),
    user_name VARCHAR(255) NOT NULL
);
select *from blogUserInfo;

insert into blogUserInfo values (0, "kim", "1234", "taehyeok1124@gmail.com", "김태혁");
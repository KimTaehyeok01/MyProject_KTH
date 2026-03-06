USE mydb;

DROP TABLE Article;

CREATE TABLE Article (
	id 	BIGINT	PRIMARY KEY AUTO_INCREMENT,
	user_name varchar(200) NOT NULL,
	title varchar(255) NOT NULL ,
	content varchar(255) NOT NULL
);

INSERT INTO Article VALUES (0, "김태혁", "글제목", "글내용");
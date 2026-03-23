
USE mydb;

drop table users_jwt;
create table users_jwt(
    id          BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    email         VARCHAR(255) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    user_role   VARCHAR(255) DEFAULT 'ROLE_USER'
);

SELECT * from users_jwt;
DESC users_jwt;

DELETE FROM users_jwt;
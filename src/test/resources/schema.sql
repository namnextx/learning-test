DROP TABLE IF EXISTS student;

CREATE TABLE student(
    id bigint(20) primary key auto_increment,
    email varchar(255),
    name varchar(255)
);
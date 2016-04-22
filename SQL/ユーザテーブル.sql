create table user(
login_id char(20) primary key not null,
password char(255) not null,
name char(10) not null,
branch_code integer not null,
dept_or_post_code integer not null,
user boolean not null
);

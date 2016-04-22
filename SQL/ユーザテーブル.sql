create table users(
id integer primary key not null auto_increment,
login_id varchar(20) not null,
password varchar(255) not null,
name char(10) not null,
branche_id integer not null,
department_id integer not null,
status boolean not null
);

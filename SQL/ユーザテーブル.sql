create table users(
id integer primary key not null auto_increment,
login_id varchar(20) not null,
password varchar(255) not null,
name char(10) not null,
branches_id integer not null,
departments_id integer not null,
status boolean not null
);

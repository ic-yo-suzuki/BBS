create table posts(
id integer primary key not null auto_increment,
users_id integer not null,
title varchar(50) not null,
text varchar(1000) not null,
category varchar(10) not null,
insert_date timestamp not null
);

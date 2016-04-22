create table posts(
id integer primary key not null auto_increment,
users_id integer not null,
title char(50) not null,
text char(1000) not null,
category char(10) not null,
post_date timestamp not null
);

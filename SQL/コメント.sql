create table comments(
id integer primary key not null auto_increment,
user_id integer not null,
posts_id integer not null,
text char(500) not null,
post_date timestamp not null
);
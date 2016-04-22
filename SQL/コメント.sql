create table comments(
id integer primary key not null auto_increment,
users_id integer not null,
posts_id integer not null,
text varchar(500) not null,
insert_date timestamp not null
);
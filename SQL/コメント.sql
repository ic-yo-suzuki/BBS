create table comments(
id integer primary key not null auto_increment,
user_id integer not null,
post_id integer not null,
text varchar(500) not null,
insert_date timestamp not null
);
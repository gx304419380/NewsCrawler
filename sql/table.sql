-- auto-generated definition
CREATE TABLE news
(
  id          INT AUTO_INCREMENT
    PRIMARY KEY,
  title       VARCHAR(128) NULL,
  url         VARCHAR(256) NULL,
  image       VARCHAR(256) NULL,
  create_date DATETIME     NULL,
  news_date   DATETIME     NULL,
  content     TEXT         NULL,
  source      VARCHAR(32)  NULL
);


create table user
(
	id int auto_increment
		primary key,
	name varchar(32) null,
	age int null,
	address varchar(32) null,
	sex varchar(32) null
)
;

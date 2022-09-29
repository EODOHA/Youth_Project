CREATE DATABASE IF NOT EXISTS free_board;
show databases;
use free_board;

create table if not exists `freeboard1`(
`id` bigint(20) not null auto_increment comment 'pk',
`title` varchar(200) not null comment '제목',
`content` text not null comment '내용',
`read_cnt` int(11) not null default 0 comment '조회수',
`writer` varchar(100) not null comment '작성자',
`createdate` datetime null default null comment '작성일',
`updatedate` datetime null default null comment '수정일',
primary key (`id`)
) engine=InnoDB default charset=utf8mb3 comment='게시판';

show tables;
drop table freeboard1;
select * from freeboard;




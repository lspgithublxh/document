create database if not exists mydatabase;
use mydatabase;
create table User{
   name varchar(20) not null,
   id int(11) not null AUTO_INCREMENT,
   primary key(id)
}
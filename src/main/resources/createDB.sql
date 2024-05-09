drop database if exists project_manager_table;
create database project_manager_table;

use project_manager;

create table project
(
    project_id   int auto_increment
        primary key,
    project_name varchar(50) null,
    date_start   date        null,
    date_end     date        null,
    cost         bigint      null
);

create table user
(
    user_id  int auto_increment
        primary key,
    name     varchar(50)                   null,
    login    varchar(50)                   null,
    password blob                          null,
    type     enum ('EMPLOYEE', 'CUSTOMER') null
);

create table user_project
(
    user_id    int null,
    project_id int null,
    constraint FK_project_id
        foreign key (project_id) references project (project_id)
            on update cascade on delete cascade,
    constraint FK_user_id
        foreign key (user_id) references user (user_id)
            on update cascade on delete cascade
);

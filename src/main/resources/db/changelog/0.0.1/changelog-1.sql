--liquibase formatted sql

--changeset gnori:1
--comment create table user
create table _user (
    id bigserial not null primary key,
    username varchar(128) not null constraint uk_user_username unique,
    email varchar(128) not null constraint uk_user_email unique,
    name varchar(128)
);
--rollback drop table _user;

--changeset gnori:2
--comment create table movie
create table movie (
    id bigserial not null primary key,
    title varchar(128) not null,
    poster_path varchar(255) not null,

    constraint uk_movie_ unique (title, poster_path)
);
--rollback drop table movie;

--changeset gnori:3
--comment create table favorites and binding with user and movie tables
create table favorites (
    user_id bigint not null constraint fk_favorites_user references _user,
    movie_id bigint not null constraint fk_favorites_movie references movie
);
--rollback drop table favorites;
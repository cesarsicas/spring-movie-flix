create table users(

    id bigserial primary key,
    login varchar(100) not null unique,
    password varchar(255) not null,
    role varchar(255) not null,
    is_active boolean not null

);
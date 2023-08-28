create table users (
    id uuid,
    name varchar(255),
    username varchar(255),
    password varchar(64),
    roles varchar(255),
    created_at timestamp,
    last_update_at timestamp,
    primary key (id)
);
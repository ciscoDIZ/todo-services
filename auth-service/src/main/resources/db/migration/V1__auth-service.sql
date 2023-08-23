create table users
(
    id             uuid,
    name           varchar(255),
    username       varchar(255),
    password       varchar,
    roles          varchar default 'user',
    created_at     timestamp,
    last_update_at timestamp,
    primary key (id)
);

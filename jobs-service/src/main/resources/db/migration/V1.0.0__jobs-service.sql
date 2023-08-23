create table todos
(
    id             uuid,
    title          varchar(255),
    description    varchar(255),
    status         varchar(255) check ( status in ('STOPPED', 'STARTED', 'PAUSED', 'SUCCESS') ),
    created_at     timestamp,
    last_update_at timestamp,
    primary key (id)
);

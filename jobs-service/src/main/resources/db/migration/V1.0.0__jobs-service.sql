create table users (
    id uuid,
    name varchar(255),
    username varchar(255),
    password varchar,
    roles varchar default 'user',
    created_at timestamp,
    updated_at timestamp,
    primary key (id)
);

create table todos (
    id uuid,
    title varchar(255),
    description varchar(255),
    status varchar(255) check ( status in ('STOPPED', 'STARTED', 'PAUSED', 'SUCCESS') ),
    created_at timestamp,
    last_update_at timestamp,
    primary key (id)
);

alter table todos
    add user_id uuid,
    add constraint users_todos_fk
        foreign key (user_id) references users (id);
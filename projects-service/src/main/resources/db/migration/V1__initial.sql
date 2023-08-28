create table projects
(
    id          uuid,
    name        varchar(255),
    description varchar(2047),
    owner       uuid,
    primary key (id)
);
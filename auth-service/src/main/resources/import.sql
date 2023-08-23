insert into users
    (id, name, username, password, created_at, last_update_at)
values ('026ec571-f271-4ab4-95e5-00b49c260795', 'juan', 'juanillo', 'juanillo', current_timestamp, current_timestamp);
insert into users
(id, name, username, password, created_at, last_update_at)
values ('34c5a104-18ca-4f6e-b219-851d6296a7a5', 'delete', 'delete', 'delete', current_timestamp, current_timestamp);
insert into todos (created_at, last_update_at, id, user_id, description, status, title)
VALUES (current_timestamp, current_timestamp,
        'a7353852-5a1e-4e01-8acc-6f268235ff1f',
        '026ec571-f271-4ab4-95e5-00b49c260795',
        'como mola',
        'STOPPED',
        'hola');
insert into todos (created_at, last_update_at, id, user_id, description, status, title)
values (current_timestamp,
        current_timestamp,
        '701cf4c5-676b-4811-9791-961476c3440d',
        null,
        'irrelevant',
        'STOPPED',
        'irrelevant');

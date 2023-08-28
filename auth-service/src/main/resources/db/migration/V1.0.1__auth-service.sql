alter table users
    alter column roles
        set not null;
alter table users
    alter column roles
        set default 'user';

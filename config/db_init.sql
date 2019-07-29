create table resume
(
    uuid      char(36) not null
        constraint resume_pk primary key,
    full_name text     not null
);

alter table resume
    owner to postgres;

create table contact_type
(
    id    serial not null
        constraint contact_type_pk primary key,
    value text   not null
);

alter table contact_type
    owner to postgres;

insert into contact_type (value)
values ('MOBILE_PHONE'),
       ('SKYPE'),
       ('EMAIL'),
       ('LINKEDIN'),
       ('GITHUB'),
       ('STACKOVERFLOW'),
       ('WEBPAGE');

create table contact
(
    id          serial   not null
        constraint contact_pk primary key,
    resume_uuid char(36) not null
        constraint contact_resume_uuid_fk references resume on delete cascade,
    type_id     int      not null
        constraint contact_contact_type_id_fk references contact_type on delete cascade,
    type        text     not null,
    value       text     not null
);

create unique index contact_resume_uuid_type_id_uindex
    on contact (resume_uuid, type_id);

alter table contact
    owner to postgres;

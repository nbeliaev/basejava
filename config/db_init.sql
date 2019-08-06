CREATE TABLE resume
(
    uuid      CHAR(36) NOT NULL
        CONSTRAINT resume_pk PRIMARY KEY,
    full_name TEXT     NOT NULL
);

CREATE TABLE contact
(
    id          SERIAL   NOT NULL
        CONSTRAINT contact_pk PRIMARY KEY,
    resume_uuid CHAR(36) NOT NULL
        CONSTRAINT contact_resume_uuid_fk references resume on delete cascade,
    type        TEXT     NOT NULL,
    value       TEXT     NOT NULL
);

CREATE UNIQUE INDEX contact_resume_uuid_type_id_uindex
    ON contact (resume_uuid, type);

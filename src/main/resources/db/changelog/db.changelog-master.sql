--liquibase formatted sql

--changeset vzagnitko:1
CREATE TABLE public.user
(
    id BIGINT NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    PRIMARY KEY (id)
)
WITH (
OIDS = FALSE
);

ALTER TABLE public."user"
    OWNER to postgres;

CREATE UNIQUE INDEX idx_user_username ON public.user (username);
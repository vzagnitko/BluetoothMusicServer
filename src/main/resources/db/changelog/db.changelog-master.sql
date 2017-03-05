--liquibase formatted sql

--changeset vzagnitko:1
CREATE TABLE public.user_role
(
  ur_id   SERIAL,
  ur_role INTEGER NOT NULL,
  PRIMARY KEY (ur_id)
)
WITH (
OIDS = FALSE
);

ALTER TABLE public.user_role
  OWNER TO postgres;

--changeset vzagnitko:2
CREATE TABLE public.user
(
  u_id            SERIAL,
  u_first_name    VARCHAR(50)  NOT NULL,
  u_last_name     VARCHAR(50)  NOT NULL,
  u_password      VARCHAR(100) NOT NULL,
  u_username      VARCHAR(50)  NOT NULL UNIQUE,
  u_register_date TIMESTAMPTZ  NOT NULL,
  u_register_ip   TEXT         NOT NULL,
  u_user_role     SERIAL REFERENCES public.user_role (ur_id) ON DELETE CASCADE,
  PRIMARY KEY (u_id)
)
WITH (
OIDS = FALSE
);

ALTER TABLE public."user"
  OWNER TO postgres;

CREATE UNIQUE INDEX idx_user_username
  ON public.user (u_username);
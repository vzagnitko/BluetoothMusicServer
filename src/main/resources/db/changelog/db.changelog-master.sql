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
--rollback TABLE public.user_role;

--changeset vzagnitko:2
CREATE TABLE public.user
(
  u_id                         SERIAL,
  u_first_name                 VARCHAR(50)  NOT NULL,
  u_last_name                  VARCHAR(50)  NOT NULL,
  u_password                   VARCHAR(100) NOT NULL,
  u_username                   VARCHAR(50)  NOT NULL UNIQUE,
  u_register_date              TIMESTAMPTZ  NOT NULL,
  u_register_ip                TEXT         NOT NULL,
  u_is_enabled                 BOOLEAN      NOT NULL DEFAULT FALSE,
  u_is_account_non_expired     BOOLEAN      NOT NULL DEFAULT TRUE,
  u_is_account_non_blocked     BOOLEAN      NOT NULL DEFAULT TRUE,
  u_is_credentials_non_expired BOOLEAN      NOT NULL DEFAULT TRUE,
  u_user_role                  SERIAL REFERENCES public.user_role (ur_id) ON DELETE CASCADE,
  PRIMARY KEY (u_id)
)
WITH (
OIDS = FALSE
);

ALTER TABLE public."user"
  OWNER TO postgres;

CREATE UNIQUE INDEX idx_user_username
  ON public.user (u_username);
CREATE INDEX idx_u_user_role
  ON public.user (u_user_role);
--rollback TABLE TABLE public.user;

--changeset vzagnitko:3
CREATE TABLE public.music
(
  m_id          SERIAL,
  m_name        VARCHAR(100) NOT NULL,
  m_is_favorite BOOLEAN      NOT NULL DEFAULT FALSE,
  m_user_id     SERIAL REFERENCES public.user (u_id) ON DELETE CASCADE,
  PRIMARY KEY (m_id)
)
WITH (
OIDS = FALSE
);

ALTER TABLE public."music"
  OWNER TO postgres;

CREATE INDEX idx_music_name
  ON public.music (m_name);
CREATE INDEX idx_m_user_id
  ON public.music (m_user_id);
--rollback TABLE TABLE public.music;
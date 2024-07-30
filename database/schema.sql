-- DROP SCHEMA IF EXISTS public CASCADE;

-- CREATE SCHEMA public;

BEGIN;

GRANT USAGE ON SCHEMA public TO backend_user;

ALTER TABLE IF EXISTS public.currency_cloud DROP CONSTRAINT IF EXISTS None;

ALTER TABLE IF EXISTS public.kyc DROP CONSTRAINT IF EXISTS None;


DROP TABLE IF EXISTS public.currency_cloud;

CREATE TABLE IF NOT EXISTS public.currency_cloud
(
    users_id bigint NOT NULL,
    uuid character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT "Currency_Cloud_pkey" PRIMARY KEY ("uuid")
    );

DROP TABLE IF EXISTS public._users;

CREATE TABLE IF NOT EXISTS public._users
(
    id bigint NOT NULL,
    address character varying(50) COLLATE pg_catalog."default" NOT NULL,
    dob character varying(50) COLLATE pg_catalog."default" NOT NULL,
    firstname character varying(50) COLLATE pg_catalog."default" NOT NULL,
    lastname character varying(50) COLLATE pg_catalog."default" NOT NULL,
    phone_number character varying(50) COLLATE pg_catalog."default" NOT NULL,
    email character varying(100) COLLATE pg_catalog."default" NOT NULL,
    password character varying(100) COLLATE pg_catalog."default" NOT NULL,
    role character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT _users_pkey PRIMARY KEY (id),
    CONSTRAINT _users_email_key UNIQUE (email)
    );

DROP TABLE IF EXISTS public.kyc;

CREATE TABLE IF NOT EXISTS public.kyc
(
    key bigint NOT NULL,
    "application_id" character varying NOT NULL,
    "work_flow_run_id" character varying NOT NULL,
    "status" character varying,
    user_id bigint NOT NULL,
    PRIMARY KEY (key)
    );

ALTER TABLE IF EXISTS public.currency_cloud
    ADD FOREIGN KEY (users_id)
    REFERENCES public._users (id) MATCH SIMPLE
    ON UPDATE NO ACTION
       ON DELETE NO ACTION
    NOT VALID;

ALTER TABLE IF EXISTS public.kyc
    ADD FOREIGN KEY (user_id)
    REFERENCES public._users (id) MATCH SIMPLE
    ON UPDATE NO ACTION
       ON DELETE NO ACTION
    NOT VALID;

CREATE SEQUENCE _users_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

GRANT USAGE, SELECT ON SEQUENCE _users_seq TO backend_user;

GRANT INSERT, SELECT, UPDATE, DELETE ON TABLE currency_cloud TO backend_user;
GRANT INSERT, SELECT, UPDATE, DELETE ON TABLE _users TO backend_user;
GRANT INSERT, SELECT, UPDATE, DELETE ON TABLE kyc TO backend_user;

END;

-- Create schema if it does not exist
CREATE SCHEMA IF NOT EXISTS public;

-- Create tables
CREATE TABLE IF NOT EXISTS public._users (
                                             id BIGINT NOT NULL PRIMARY KEY,
                                             address VARCHAR(50) NOT NULL,
                                             dob VARCHAR(50) NOT NULL,
                                             firstname VARCHAR(50) NOT NULL,
                                             lastname VARCHAR(50) NOT NULL,
                                             phone_number VARCHAR(50) NOT NULL,
                                             email VARCHAR(100) NOT NULL UNIQUE,
                                             password VARCHAR(100) NOT NULL,
                                             role VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS public.currency_cloud (
                                                     users_id BIGINT NOT NULL,
                                                     uuid VARCHAR NOT NULL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS public.kyc (
                                          key BIGINT NOT NULL PRIMARY KEY,
                                          application_id VARCHAR NOT NULL,
                                          work_flow_run_id VARCHAR NOT NULL,
                                          status VARCHAR,
                                          user_id BIGINT NOT NULL
);

-- Create sequences
CREATE SEQUENCE IF NOT EXISTS public._users_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Add constraints
ALTER TABLE public.currency_cloud
    ADD CONSTRAINT currency_cloud_users_id_fkey
        FOREIGN KEY (users_id) REFERENCES public._users(id);

ALTER TABLE public.kyc
    ADD CONSTRAINT kyc_user_id_fkey
        FOREIGN KEY (user_id) REFERENCES public._users(id);


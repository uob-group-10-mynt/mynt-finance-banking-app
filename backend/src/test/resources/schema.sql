--
-- PostgreSQL database dump
--

-- Dumped from database version 16.3
-- Dumped by pg_dump version 16.3 (Ubuntu 16.3-1.pgdg22.04+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: public; Type: SCHEMA; Schema: -; Owner: -
--

-- *not* creating schema, since initdb creates it


--
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON SCHEMA public IS '';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: _users; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public._users (
    id bigint NOT NULL,
    address character varying(50) NOT NULL,
    dob character varying(50) NOT NULL,
    firstname character varying(50) NOT NULL,
    lastname character varying(50) NOT NULL,
    phone_number character varying(50) NOT NULL,
    email character varying(100) NOT NULL,
    password character varying(100) NOT NULL,
    role character varying(255)
);


--
-- Name: _users_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public._users ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public._users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: currency_cloud; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.currency_cloud (
    users_id bigint NOT NULL,
    uuid character varying NOT NULL
);


--
-- Name: kyc; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.kyc (
    key bigint NOT NULL,
    application_id character varying NOT NULL,
    work_flow_run_id character varying NOT NULL,
    status character varying,
    user_id bigint NOT NULL
);


--
-- Name: kyc_key_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.kyc ALTER COLUMN key ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.kyc_key_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: currency_cloud Currency_Cloud_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.currency_cloud
    ADD CONSTRAINT "Currency_Cloud_pkey" PRIMARY KEY (uuid);


--
-- Name: _users _users_email_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public._users
    ADD CONSTRAINT _users_email_key UNIQUE (email);


--
-- Name: _users _users_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public._users
    ADD CONSTRAINT _users_pkey PRIMARY KEY (id);


--
-- Name: kyc kyc_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.kyc
    ADD CONSTRAINT kyc_pkey PRIMARY KEY (key);


--
-- Name: currency_cloud currency_cloud_users_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.currency_cloud
    ADD CONSTRAINT currency_cloud_users_id_fkey FOREIGN KEY (users_id) REFERENCES public._users(id) NOT VALID;


--
-- Name: kyc kyc_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.kyc
    ADD CONSTRAINT kyc_user_id_fkey FOREIGN KEY (user_id) REFERENCES public._users(id) NOT VALID;


--
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: -
--

REVOKE USAGE ON SCHEMA public FROM PUBLIC;
GRANT USAGE ON SCHEMA public TO backend_user;


--
-- Name: TABLE _users; Type: ACL; Schema: public; Owner: -
--

GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public._users TO backend_user;


--
-- Name: TABLE currency_cloud; Type: ACL; Schema: public; Owner: -
--

GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.currency_cloud TO backend_user;


--
-- Name: TABLE kyc; Type: ACL; Schema: public; Owner: -
--

GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.kyc TO backend_user;


--
-- PostgreSQL database dump complete
--


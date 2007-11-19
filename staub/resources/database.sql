--
-- PostgreSQL database dump
--

SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- Name: staubdb; Type: DATABASE; Schema: -; Owner: staub
--

CREATE DATABASE staubdb WITH TEMPLATE = template0 ENCODING = 'UTF8';


ALTER DATABASE staubdb OWNER TO staub;

\connect staubdb

SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'Standard public schema';


--
-- Name: staub; Type: SCHEMA; Schema: -; Owner: staub
--

CREATE SCHEMA staub;


ALTER SCHEMA staub OWNER TO staub;

--
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: -; Owner: postgres
--

CREATE PROCEDURAL LANGUAGE plpgsql;


ALTER PROCEDURAL LANGUAGE plpgsql OWNER TO postgres;

SET search_path = staub, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: question; Type: TABLE; Schema: staub; Owner: staub; Tablespace: 
--

CREATE TABLE question (
    id integer NOT NULL,
    fk_test integer NOT NULL,
    name character varying(128),
    description text,
    definition text NOT NULL
);


ALTER TABLE staub.question OWNER TO staub;

--
-- Name: seq_question; Type: SEQUENCE; Schema: staub; Owner: staub
--

CREATE SEQUENCE seq_question
    START WITH 1
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;


ALTER TABLE staub.seq_question OWNER TO staub;

--
-- Name: seq_test; Type: SEQUENCE; Schema: staub; Owner: staub
--

CREATE SEQUENCE seq_test
    START WITH 1
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;


ALTER TABLE staub.seq_test OWNER TO staub;

--
-- Name: seq_user; Type: SEQUENCE; Schema: staub; Owner: staub
--

CREATE SEQUENCE seq_user
    START WITH 1
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;


ALTER TABLE staub.seq_user OWNER TO staub;

--
-- Name: test; Type: TABLE; Schema: staub; Owner: staub; Tablespace: 
--

CREATE TABLE test (
    id integer NOT NULL,
    name character varying(128),
    description text
);


ALTER TABLE staub.test OWNER TO staub;

--
-- Name: user; Type: TABLE; Schema: staub; Owner: staub; Tablespace: 
--

CREATE TABLE "user" (
    id integer NOT NULL,
    username character varying(64) NOT NULL,
    password character varying(64) NOT NULL
);


ALTER TABLE staub."user" OWNER TO staub;

--
-- Name: ak_user; Type: CONSTRAINT; Schema: staub; Owner: staub; Tablespace: 
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT ak_user UNIQUE (username);


--
-- Name: pk_question; Type: CONSTRAINT; Schema: staub; Owner: staub; Tablespace: 
--

ALTER TABLE ONLY question
    ADD CONSTRAINT pk_question PRIMARY KEY (id);


--
-- Name: pk_test; Type: CONSTRAINT; Schema: staub; Owner: staub; Tablespace: 
--

ALTER TABLE ONLY test
    ADD CONSTRAINT pk_test PRIMARY KEY (id);


--
-- Name: pk_user; Type: CONSTRAINT; Schema: staub; Owner: staub; Tablespace: 
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT pk_user PRIMARY KEY (id);


--
-- Name: fki_test; Type: INDEX; Schema: staub; Owner: staub; Tablespace: 
--

CREATE INDEX fki_test ON question USING btree (fk_test);


--
-- Name: fk_test; Type: FK CONSTRAINT; Schema: staub; Owner: staub
--

ALTER TABLE ONLY question
    ADD CONSTRAINT fk_test FOREIGN KEY (fk_test) REFERENCES test(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--


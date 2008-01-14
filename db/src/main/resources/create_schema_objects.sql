CREATE TABLE question (
    id integer NOT NULL,
    fk_test integer NOT NULL,
    name character varying(128),
    definition text NOT NULL
);

CREATE TABLE test (
    id integer NOT NULL,
    name character varying(128),
    description text,
    time_limit integer,
    pass_score integer,
    questions_count integer,
    CONSTRAINT chk_pass_score CHECK (((pass_score > 0) AND (pass_score <= 100))),
    CONSTRAINT chk_questions_count CHECK ((questions_count > 0)),
    CONSTRAINT chk_time_limit CHECK ((time_limit > 0))
);

CREATE TABLE "user" (
    id integer NOT NULL,
    username character varying(64) NOT NULL,
    password character varying(64) NOT NULL
);

CREATE SEQUENCE seq_question
    START WITH 1
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;

CREATE SEQUENCE seq_test
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;

CREATE SEQUENCE seq_user
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;

ALTER TABLE ONLY "user"
    ADD CONSTRAINT ak_user UNIQUE (username);

ALTER TABLE ONLY question
    ADD CONSTRAINT pk_question PRIMARY KEY (id);

ALTER TABLE ONLY test
    ADD CONSTRAINT pk_test PRIMARY KEY (id);

ALTER TABLE ONLY "user"
    ADD CONSTRAINT pk_user PRIMARY KEY (id);

CREATE INDEX fki_test ON question USING btree (fk_test);

ALTER TABLE ONLY question
    ADD CONSTRAINT fk_test FOREIGN KEY (fk_test) REFERENCES test(id) ON UPDATE CASCADE ON DELETE CASCADE;
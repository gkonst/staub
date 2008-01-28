-- Tables
CREATE TABLE question (
    id integer NOT NULL,
    fk_test integer NOT NULL,
    name character varying(128),
    time_limit integer,
    definition text NOT NULL,
    created date,
    created_by character varying(64),
    modified date,
    modified_by character varying(64)
);

CREATE TABLE question_trace (
    id integer NOT NULL,
    fk_test_trace integer NOT NULL,
    fk_question integer NOT NULL,
    started date NOT NULL,
    finished date,
    answer text NOT NULL
);

CREATE TABLE test (
    id integer NOT NULL,
    name character varying(128),
    description text,
    time_limit integer,
    pass_score integer,
    questions_count integer,
    created date,
    created_by character varying(64),
    modified date,
    modified_by character varying(64),
    CONSTRAINT chk_pass_score CHECK (((pass_score > 0) AND (pass_score <= 100))),
    CONSTRAINT chk_questions_count CHECK ((questions_count > 0)),
    CONSTRAINT chk_time_limit CHECK ((time_limit > 0))
);

CREATE TABLE test_trace (
    id integer NOT NULL,
    fk_test integer NOT NULL,
    fk_user integer NOT NULL,
    session_id character varying(64) NOT NULL,
    started date NOT NULL,
    finished date
);

CREATE TABLE "user" (
    id integer NOT NULL,
    username character varying(64) NOT NULL,
    password character varying(64) NOT NULL
);

-- Sequences
CREATE SEQUENCE seq_question
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;

CREATE SEQUENCE seq_question_trace
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;

CREATE SEQUENCE seq_test
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;

CREATE SEQUENCE seq_test_trace
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;

CREATE SEQUENCE seq_user
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;

-- Constraints
ALTER TABLE ONLY question
    ADD CONSTRAINT pk_question PRIMARY KEY (id);

ALTER TABLE ONLY question_trace
    ADD CONSTRAINT pk_question_trace PRIMARY KEY (id);

ALTER TABLE ONLY test
    ADD CONSTRAINT pk_test PRIMARY KEY (id);

ALTER TABLE ONLY test_trace
    ADD CONSTRAINT pk_test_trace PRIMARY KEY (id);

ALTER TABLE ONLY "user"
    ADD CONSTRAINT pk_user PRIMARY KEY (id);

ALTER TABLE ONLY "user"
    ADD CONSTRAINT ak_user UNIQUE (username);

ALTER TABLE ONLY question
    ADD CONSTRAINT fk_test FOREIGN KEY (fk_test) REFERENCES test(id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY question_trace
    ADD CONSTRAINT fk_question FOREIGN KEY (fk_question) REFERENCES question(id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY question_trace
    ADD CONSTRAINT fk_test_trace FOREIGN KEY (fk_test_trace) REFERENCES test_trace(id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY test_trace
    ADD CONSTRAINT fk_test FOREIGN KEY (fk_test) REFERENCES test(id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY test_trace
    ADD CONSTRAINT fk_user FOREIGN KEY (fk_user) REFERENCES "user"(id) ON UPDATE CASCADE ON DELETE CASCADE;

-- Indexes
CREATE INDEX fki_question__test ON question USING btree (fk_test);

CREATE INDEX fki_question_trace__question ON question_trace USING btree (fk_question);

CREATE INDEX fki_question_trace__test_trace ON question_trace USING btree (fk_test_trace);

CREATE INDEX fki_test_trace__test ON test_trace USING btree (fk_test);

CREATE INDEX fki_test_trace__user ON test_trace USING btree (fk_user);
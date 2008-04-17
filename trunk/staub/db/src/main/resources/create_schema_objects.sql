-- Tables
CREATE TABLE role (
   id INTEGER NOT NULL,
   description CHARACTER VARYING(256),
   PRIMARY KEY (id)
);

CREATE TABLE discipline (
    id INTEGER NOT NULL,
    name CHARACTER VARYING(256),
    code INTEGER,
    PRIMARY KEY (id),
    CONSTRAINT ak_discipline_code UNIQUE (code)
);

CREATE TABLE category (
    id INTEGER NOT NULL,
    fk_discipline INTEGER NOT NULL,
    name CHARACTER VARYING(256),
    code INTEGER,
    PRIMARY KEY (id),
    CONSTRAINT ak_category_code UNIQUE (code),
    CONSTRAINT fk_discipline FOREIGN KEY (fk_discipline) REFERENCES discipline (id)
);

CREATE TABLE topic (
    id INTEGER NOT NULL,
    fk_category INTEGER NOT NULL,
    name CHARACTER VARYING(256),
    code INTEGER,
    PRIMARY KEY (id),
    CONSTRAINT ak_topic_code UNIQUE (code),
    CONSTRAINT fk_category FOREIGN KEY (fk_category) REFERENCES category (id)
);

CREATE TABLE difficulty (
    id INTEGER NOT NULL,
    name CHARACTER VARYING(256),
    code INTEGER,
    PRIMARY KEY (id),
    CONSTRAINT ak_difficulty_code UNIQUE (code)
);

CREATE TABLE question (
    id INTEGER NOT NULL,
--    fk_discipline INTEGER NOT NULL,
--    fk_category INTEGER NOT NULL,
    fk_topic INTEGER NOT NULL,
    fk_difficulty INTEGER NOT NULL,
    name CHARACTER VARYING(256),
    time_limit INTEGER,
    definition BYTEA,
    active BOOLEAN,
    created TIMESTAMP,
    created_by CHARACTER VARYING(64),
    modified TIMESTAMP,
    modified_by CHARACTER VARYING(64),
    CONSTRAINT pk_question PRIMARY KEY (id),
--    CONSTRAINT fk_discipline FOREIGN KEY (fk_discipline) REFERENCES discipline (id),
--    CONSTRAINT fk_category FOREIGN KEY (fk_category) REFERENCES category (id),
    CONSTRAINT fk_topic FOREIGN KEY (fk_topic) REFERENCES topic (id),
    CONSTRAINT fk_difficulty FOREIGN KEY (fk_difficulty) REFERENCES difficulty (id)
);

CREATE TABLE test (
    id INTEGER NOT NULL,
    fk_category INTEGER, -- intentionally nullable
    name CHARACTER VARYING(256),
    description TEXT,
    time_limit INTEGER,
    pass_score INTEGER,
--    questions_count INTEGER,
    active BOOLEAN,
    created TIMESTAMP,
    created_by CHARACTER VARYING(64),
    modified TIMESTAMP,
    modified_by CHARACTER VARYING(64),
    CONSTRAINT pk_test PRIMARY KEY (id),
    CONSTRAINT fk_category FOREIGN KEY (fk_category) REFERENCES category (id),
    CONSTRAINT chk_pass_score CHECK ((pass_score > 0) AND (pass_score <= 100)),
--    CONSTRAINT chk_questions_count CHECK (questions_count >= 0),
    CONSTRAINT chk_time_limit CHECK (time_limit > 0)
);

CREATE TABLE test_topic (
    fk_test INTEGER NOT NULL,
    fk_topic INTEGER NOT NULL,
    CONSTRAINT pk_test_topic PRIMARY KEY (fk_test, fk_topic),
    CONSTRAINT fk_test FOREIGN KEY (fk_test) REFERENCES test (id),
    CONSTRAINT fk_topic FOREIGN KEY (fk_topic) REFERENCES topic (id)
);

CREATE TABLE test_difficulty (
    fk_test INTEGER NOT NULL,
    fk_difficulty INTEGER NOT NULL,
    questions_count INTEGER,
    CONSTRAINT pk_test_difficulty PRIMARY KEY (fk_test, fk_difficulty),
    CONSTRAINT fk_test FOREIGN KEY (fk_test) REFERENCES test (id),
    CONSTRAINT fk_difficulty FOREIGN KEY (fk_difficulty) REFERENCES difficulty (id)
);

CREATE TABLE "user" (
    id INTEGER NOT NULL,
    fk_role INTEGER NOT NULL,
    username CHARACTER VARYING(64) NOT NULL,
    password CHARACTER VARYING(64) NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id),
    CONSTRAINT fk_role FOREIGN KEY (fk_role) REFERENCES role (id)
);

CREATE TABLE "group" (
    id INTEGER NOT NULL,
    name CHARACTER VARYING(16) NOT NULL,
    CONSTRAINT pk_group PRIMARY KEY (id),
    CONSTRAINT ak_group_name UNIQUE (name)
);

CREATE TABLE student (
    id INTEGER NOT NULL,
    fk_group INTEGER NOT NULL,
    name CHARACTER VARYING(256) NOT NULL,
    code CHARACTER VARYING(16) NOT NULL,
    CONSTRAINT pk_student PRIMARY KEY (id),
    CONSTRAINT ak_student_code UNIQUE (code),
    CONSTRAINT fk_group FOREIGN KEY (fk_group) REFERENCES "group" (id)
);

CREATE TABLE assignment (
    id INTEGER NOT NULL,
    fk_test INTEGER NOT NULL,
    fk_student INTEGER NOT NULL,
    test_begin TIMESTAMP,
    test_end TIMESTAMP,
    test_started BOOLEAN,
    CONSTRAINT pk_assignment PRIMARY KEY (id),
    CONSTRAINT fk_test FOREIGN KEY (fk_test) REFERENCES test (id),
    CONSTRAINT fk_student FOREIGN KEY (fk_student) REFERENCES student (id)
);

CREATE TABLE test_trace (
    id INTEGER NOT NULL,
    fk_test INTEGER NOT NULL,
    fk_student INTEGER NOT NULL,
--    session_id CHARACTER VARYING(64),
    score INTEGER,
    test_passed BOOLEAN,
    started TIMESTAMP,
    finished TIMESTAMP,
    CONSTRAINT pk_test_trace PRIMARY KEY (id),
    CONSTRAINT fk_test FOREIGN KEY (fk_test) REFERENCES test (id),
    CONSTRAINT fk_student FOREIGN KEY (fk_student) REFERENCES student (id)
);

CREATE TABLE question_trace (
    id INTEGER NOT NULL,
    fk_test_trace INTEGER NOT NULL,
    fk_question INTEGER NOT NULL,
    correct BOOLEAN,
    started TIMESTAMP,
    finished TIMESTAMP,
    answer BYTEA,
    part INTEGER,
    CONSTRAINT pk_question_trace PRIMARY KEY (id),
    CONSTRAINT fk_test_trace FOREIGN KEY (fk_test_trace) REFERENCES test_trace (id),
    CONSTRAINT fk_question FOREIGN KEY (fk_question) REFERENCES question (id)
);

--CREATE TABLE user_history (
--    id INTEGER NOT NULL,
--    fk_test_trace INTEGER NOT NULL,
--    name CHARACTER VARYING(256),
--    score INTEGER,
--    test_passed BOOLEAN,
--    test_date TIMESTAMP,
--    CONSTRAINT pk_user_history PRIMARY KEY (id),
--    CONSTRAINT fk_test_trace FOREIGN KEY (fk_test_trace) REFERENCES test_trace (id)
--);

-- Sequences
CREATE SEQUENCE seq_discipline
    INCREMENT BY 1
    MAXVALUE 2147483647
    MINVALUE 1
    CACHE 1;

CREATE SEQUENCE seq_category
    INCREMENT BY 1
    MAXVALUE 2147483647
    MINVALUE 1
    CACHE 1;

CREATE SEQUENCE seq_topic
    INCREMENT BY 1
    MAXVALUE 2147483647
    MINVALUE 1
    CACHE 1;

CREATE SEQUENCE seq_difficulty
    INCREMENT BY 1
    MAXVALUE 2147483647
    MINVALUE 1
    CACHE 1;

CREATE SEQUENCE seq_question
    INCREMENT BY 1
    MAXVALUE 2147483647
    MINVALUE 1
    CACHE 1;

CREATE SEQUENCE seq_test
    INCREMENT BY 1
    MAXVALUE 2147483647
    MINVALUE 1
    CACHE 1;

CREATE SEQUENCE seq_user
    INCREMENT BY 1
    MAXVALUE 2147483647
    MINVALUE 1
    CACHE 1;

CREATE SEQUENCE seq_student
    INCREMENT BY 1
    MAXVALUE 2147483647
    MINVALUE 1
    CACHE 1;

CREATE SEQUENCE seq_group
    INCREMENT BY 1
    MAXVALUE 2147483647
    MINVALUE 1
    CACHE 1;

CREATE SEQUENCE seq_test_trace
    INCREMENT BY 1
    MAXVALUE 2147483647
    MINVALUE 1
    CACHE 1;
    
CREATE SEQUENCE seq_question_trace
    INCREMENT BY 1
    MAXVALUE 2147483647
    MINVALUE 1
    CACHE 1;

--CREATE SEQUENCE seq_user_history
--    INCREMENT BY 1
--   MAXVALUE 2147483647
--    MINVALUE 1
--    CACHE 1;
    
CREATE SEQUENCE seq_assignment
    INCREMENT BY 1
    MAXVALUE 2147483647
    MINVALUE 1
    CACHE 1;

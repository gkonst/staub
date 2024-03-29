-- Tables
CREATE TABLE role (
   id INTEGER NOT NULL,
   description CHARACTER VARYING(256) NOT NULL,
   CONSTRAINT pk_role PRIMARY KEY (id)
);

CREATE TABLE discipline (
    id INTEGER NOT NULL,
    name CHARACTER VARYING(256) NOT NULL,
    code CHARACTER VARYING(16) NOT NULL,
    CONSTRAINT pk_discipline PRIMARY KEY (id),
    CONSTRAINT ak_discipline__code UNIQUE (code)
);

CREATE TABLE category (
    id INTEGER NOT NULL,
    fk_discipline INTEGER NOT NULL,
    name CHARACTER VARYING(256) NOT NULL,
    code CHARACTER VARYING(16) NOT NULL,
    CONSTRAINT pk_category PRIMARY KEY (id),
    CONSTRAINT ak_category__code UNIQUE (code),
    CONSTRAINT fk_discipline FOREIGN KEY (fk_discipline) REFERENCES discipline (id)
);

CREATE TABLE topic (
    id INTEGER NOT NULL,
    fk_category INTEGER NOT NULL,
    name CHARACTER VARYING(256) NOT NULL,
    code CHARACTER VARYING(16) NOT NULL,
    CONSTRAINT pk_topic PRIMARY KEY (id),
    CONSTRAINT ak_topic__code UNIQUE (code),
    CONSTRAINT fk_category FOREIGN KEY (fk_category) REFERENCES category (id)
);

CREATE TABLE difficulty (
    id INTEGER NOT NULL,
    name CHARACTER VARYING(256) NOT NULL,
    code INTEGER NOT NULL,
    CONSTRAINT pk_difficulty PRIMARY KEY (id),
    CONSTRAINT ak_difficulty__code UNIQUE (code)
);

CREATE TABLE question (
    id INTEGER NOT NULL,
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
    CONSTRAINT fk_topic FOREIGN KEY (fk_topic) REFERENCES topic (id),
    CONSTRAINT fk_difficulty FOREIGN KEY (fk_difficulty) REFERENCES difficulty (id)
);

CREATE TABLE question_statistics (
    fk_question INTEGER NOT NULL,
    total_answers INTEGER,
    correct_answers INTEGER,
    correct_answers_pc INTEGER,
    n1 INTEGER,
    n2 INTEGER,
    n3 INTEGER,
    n4 INTEGER,
    k1 REAL,
    k2 REAL,
    k3 REAL,
    k4 REAL,
    last_update TIMESTAMP,
    CONSTRAINT pk_question_statistics PRIMARY KEY (fk_question),
    CONSTRAINT fk_question FOREIGN KEY (fk_question) REFERENCES question (id) ON DELETE CASCADE
);

CREATE TABLE test (
    id INTEGER NOT NULL,
    fk_category INTEGER NOT NULL,
    name CHARACTER VARYING(256),
    description TEXT,
    time_limit INTEGER,
    active BOOLEAN,
    created TIMESTAMP,
    created_by CHARACTER VARYING(64),
    modified TIMESTAMP,
    modified_by CHARACTER VARYING(64),
    CONSTRAINT pk_test PRIMARY KEY (id),
    CONSTRAINT fk_category FOREIGN KEY (fk_category) REFERENCES category (id),
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
    pass_score INTEGER,
    CONSTRAINT pk_test_difficulty PRIMARY KEY (fk_test, fk_difficulty),
    CONSTRAINT fk_test FOREIGN KEY (fk_test) REFERENCES test (id),
    CONSTRAINT fk_difficulty FOREIGN KEY (fk_difficulty) REFERENCES difficulty (id)
);

CREATE TABLE test_statistics (
    fk_test INTEGER NOT NULL,
    total_answers INTEGER,
    correct_answers INTEGER,
    correct_answers_pc INTEGER,
    last_update TIMESTAMP,
    CONSTRAINT pk_test_statistics PRIMARY KEY (fk_test),
    CONSTRAINT fk_test FOREIGN KEY (fk_test) REFERENCES test (id) ON DELETE CASCADE
);

CREATE TABLE "user" (
    id INTEGER NOT NULL,
    fk_role INTEGER NOT NULL,
    username CHARACTER VARYING(64) NOT NULL,
    password CHARACTER VARYING(64) NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id),
    CONSTRAINT ak_user__username UNIQUE (username),
    CONSTRAINT fk_role FOREIGN KEY (fk_role) REFERENCES role (id)
);

CREATE TABLE "group" (
    id INTEGER NOT NULL,
    name CHARACTER VARYING(16) NOT NULL,
    CONSTRAINT pk_group PRIMARY KEY (id),
    CONSTRAINT ak_group__name UNIQUE (name)
);

CREATE TABLE student (
    id INTEGER NOT NULL,
    fk_group INTEGER NOT NULL,
    name CHARACTER VARYING(256) NOT NULL,
    code CHARACTER VARYING(16) NOT NULL,
    active BOOLEAN,
    CONSTRAINT pk_student PRIMARY KEY (id),
    CONSTRAINT ak_student__code UNIQUE (code),
    CONSTRAINT fk_group FOREIGN KEY (fk_group) REFERENCES "group" (id)
);

CREATE INDEX ix_student__name ON student (name);

CREATE TABLE assignment (
    id INTEGER NOT NULL,
    fk_test INTEGER NOT NULL,
    fk_student INTEGER NOT NULL,
    test_begin TIMESTAMP,
    test_end TIMESTAMP,
    CONSTRAINT pk_assignment PRIMARY KEY (id),
    CONSTRAINT fk_test FOREIGN KEY (fk_test) REFERENCES test (id),
    CONSTRAINT fk_student FOREIGN KEY (fk_student) REFERENCES student (id)
);

CREATE TABLE test_trace (
    id INTEGER NOT NULL,
    fk_test INTEGER NOT NULL,
    fk_student INTEGER NOT NULL,
    fk_assignment INTEGER, -- intentionally nullable
    score INTEGER,
    test_passed BOOLEAN,
    started TIMESTAMP,
    finished TIMESTAMP,
    CONSTRAINT pk_test_trace PRIMARY KEY (id),
    CONSTRAINT fk_test FOREIGN KEY (fk_test) REFERENCES test (id),
    CONSTRAINT fk_student FOREIGN KEY (fk_student) REFERENCES student (id),
    CONSTRAINT fk_assignment FOREIGN KEY (fk_assignment) REFERENCES assignment (id)
);

CREATE INDEX ix_test_trace__started ON test_trace (started);

CREATE TABLE question_trace (
    id INTEGER NOT NULL,
    fk_test_trace INTEGER NOT NULL,
    fk_question INTEGER NOT NULL,
    correct BOOLEAN,
    started TIMESTAMP,
    finished TIMESTAMP,
    total_time INTEGER,
    answer BYTEA,
    part INTEGER,
    CONSTRAINT pk_question_trace PRIMARY KEY (id),
    CONSTRAINT fk_test_trace FOREIGN KEY (fk_test_trace) REFERENCES test_trace (id),
    CONSTRAINT fk_question FOREIGN KEY (fk_question) REFERENCES question (id)
);

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

CREATE SEQUENCE seq_assignment
    INCREMENT BY 1
    MAXVALUE 2147483647
    MINVALUE 1
    CACHE 1;

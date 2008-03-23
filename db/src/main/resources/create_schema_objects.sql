-- Tables
CREATE TABLE discipline (
    id INTEGER NOT NULL,
    name CHARACTER VARYING(256),
    code INTEGER,
    PRIMARY KEY (id)
);

CREATE TABLE category (
    id INTEGER NOT NULL,
    name CHARACTER VARYING(256),
    code INTEGER,
    PRIMARY KEY (id)
);

CREATE TABLE difficulty (
    id INTEGER NOT NULL,
    name CHARACTER VARYING(256),
    code INTEGER,
    PRIMARY KEY (id)
);

CREATE TABLE question (
    id INTEGER NOT NULL,
    fk_discipline INTEGER NOT NULL,
    fk_category INTEGER NOT NULL,
    fk_difficulty INTEGER NOT NULL,
    name CHARACTER VARYING(256),
    time_limit INTEGER,
    definition TEXT NOT NULL,
    created DATE,
    created_by CHARACTER VARYING(64),
    modified DATE,
    modified_by CHARACTER VARYING(64),
    CONSTRAINT pk_question PRIMARY KEY (id),
    CONSTRAINT fk_discipline FOREIGN KEY (fk_discipline) REFERENCES discipline (id),
    CONSTRAINT fk_category FOREIGN KEY (fk_category) REFERENCES category (id),
    CONSTRAINT fk_difficulty FOREIGN KEY (fk_difficulty) REFERENCES difficulty (id)
);

CREATE TABLE test (
    id INTEGER NOT NULL,
    name CHARACTER VARYING(256),
    description TEXT,
    time_limit INTEGER,
    pass_score INTEGER,
    questions_count INTEGER,
	selector_type INTEGER,
    selector_count INTEGER,
    created DATE,
    created_by CHARACTER VARYING(64),
    modified DATE,
    modified_by CHARACTER VARYING(64),
    CONSTRAINT pk_test PRIMARY KEY (id),
    CONSTRAINT chk_pass_score CHECK (((pass_score > 0) AND (pass_score <= 100))),
    CONSTRAINT chk_questions_count CHECK ((questions_count > 0)),
    CONSTRAINT chk_time_limit CHECK ((time_limit > 0))
);

CREATE TABLE test_question (
    fk_test INTEGER NOT NULL,
    fk_question INTEGER NOT NULL,
    CONSTRAINT pk_test_question PRIMARY KEY (fk_test, fk_question),
    CONSTRAINT fk_test FOREIGN KEY (fk_test) REFERENCES test (id),
    CONSTRAINT fk_question FOREIGN KEY (fk_question) REFERENCES question (id)
);

CREATE TABLE "user" (
    id INTEGER NOT NULL,
    username CHARACTER VARYING(64) NOT NULL,
    password CHARACTER VARYING(64) NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE test_trace (
    id INTEGER NOT NULL,
    fk_test INTEGER NOT NULL,
    fk_user INTEGER NOT NULL,
    session_id CHARACTER VARYING(64),
    score INTEGER,
    test_passed NUMERIC(1),
    started DATE,
    finished DATE,
    CONSTRAINT pk_test_trace PRIMARY KEY (id),
    CONSTRAINT fk_test FOREIGN KEY (fk_test) REFERENCES test (id),
    CONSTRAINT fk_user FOREIGN KEY (fk_user) REFERENCES "user" (id)
);

CREATE TABLE question_trace (
    id INTEGER NOT NULL,
    fk_test_trace INTEGER NOT NULL,
    fk_question INTEGER NOT NULL,
    correct NUMERIC(1),
    started DATE,
    finished DATE,
    answer TEXT,
    CONSTRAINT pk_question_trace PRIMARY KEY (id),
    CONSTRAINT fk_test_trace FOREIGN KEY (fk_test_trace) REFERENCES test_trace (id),
    CONSTRAINT fk_question FOREIGN KEY (fk_question) REFERENCES question (id)
);

CREATE TABLE user_history (
    id INTEGER NOT NULL,
    fk_test_trace INTEGER, -- intentionally nullable
    name CHARACTER VARYING(256),
    score INTEGER,
    test_passed NUMERIC(1),
    test_date DATE,
    CONSTRAINT pk_user_history PRIMARY KEY (id),
    CONSTRAINT fk_test_trace FOREIGN KEY (fk_test_trace) REFERENCES test_trace (id)
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

CREATE SEQUENCE seq_user_history
    INCREMENT BY 1
    MAXVALUE 2147483647
    MINVALUE 1
    CACHE 1;

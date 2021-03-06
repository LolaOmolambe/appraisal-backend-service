CREATE TABLE IF NOT EXISTS questions (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    question_text TEXT NOT NULL,
    deleted BOOLEAN DEFAULT FALSE ,
    created_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_question PRIMARY KEY (id)
);
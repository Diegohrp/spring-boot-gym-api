CREATE TABLE trainings(
    id BIGSERIAL PRIMARY KEY,
    trainee_id BIGINT,
    trainer_id BIGINT,
    type_id BIGINT,
    name VARCHAR(200),
    date DATE,
    duration SMALLINT,
    FOREIGN KEY (trainee_id) REFERENCES trainees(id),
    FOREIGN KEY (trainer_id) REFERENCES trainers(id),
    FOREIGN KEY (type_id) REFERENCES training_types(id)
);
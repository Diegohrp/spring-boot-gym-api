CREATE TABLE trainers(
    id BIGSERIAL PRIMARY KEY,
    speciality BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    FOREIGN KEY (speciality) REFERENCES training_types(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);
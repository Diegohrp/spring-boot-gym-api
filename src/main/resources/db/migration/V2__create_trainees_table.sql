CREATE TABLE trainees (
    id BIGSERIAL PRIMARY KEY,
    date_of_birth DATE,
    address VARCHAR(200),
    user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
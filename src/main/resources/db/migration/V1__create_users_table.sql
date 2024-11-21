CREATE TABLE users(
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    username VARCHAR(150) NOT NULL,
    password VARCHAR(20) NOT NULL,
    is_active BOOLEAN
);
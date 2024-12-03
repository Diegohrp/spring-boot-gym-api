INSERT INTO users (first_name, last_name, username, password, is_active)
VALUES ('John', 'Doe', 'John.Doe', 'password123', true),
       ('Jane', 'Smith', 'Jane.Smith', 'password123', true),
       ('Jim', 'Beam', 'Jim.Beam', 'password123', true),
       ('Jack', 'Daniels', 'Jack.Daniels', 'password123', true),
       ('Johnny', 'Walker', 'Johnny.Walker', 'password123', true),
       ('Jill', 'Valentine', 'Jill.Valentine', 'password123', true),
       ('Chris', 'Redfield', 'Chris.Redfield', 'password123', true),
       ('Leon', 'Kennedy', 'Leon.Kennedy', 'password123', true),
       ('Claire', 'Redfield', 'Claire.Redfield', 'password123', true),
       ('Ada', 'Wong', 'Ada.Wong', 'password123', true),
       ('Mike', 'Johnson', 'Mike.Johnson', 'password123', true),
       ('Emma', 'Waters', 'Emma.Waters', 'password123', true),
       ('Liam', 'Turner', 'Liam.Turner', 'password123', true),
       ('Sophia', 'Martinez', 'Sophia.Martinez', 'password123', true),
       ('Oliver', 'Lee', 'Oliver.Lee', 'password123', true),
       ('Ava', 'Brown', 'Ava.Brown', 'password123', true),
       ('Isabella', 'Davis', 'Isabella.Davis', 'password123', true),
       ('Mason', 'Wilson', 'Mason.Wilson', 'password123', true),
       ('Charlotte', 'Garcia', 'Charlotte.Garcia', 'password123', true),
       ('Lucas', 'Martinez', 'Lucas.Martinez', 'password123', true);


INSERT INTO training_types (name)
VALUES ('Strength'),
       ('Cardio'),
       ('Flexibility'),
       ('Balance'),
       ('Endurance'),
       ('Power'),
       ('Agility'),
       ('Core'),
       ('Stamina'),
       ('Mindfulness');


INSERT INTO trainees (date_of_birth, address, user_id)
VALUES ('1985-02-15', '123 Elm Street', 1),
       ('1990-06-20', '456 Oak Street', 2),
       ('1995-08-30', '789 Pine Street', 3),
       ('1995-08-30', '789 Pine Street', 4),
       ('1995-08-30', '789 Pine Street', 5);


INSERT INTO trainers (speciality, user_id)
VALUES (3, 6),
       (4, 7),
       (5, 8),
       (6, 9),
       (7, 10),
       (8, 11),
       (9, 12),
       (10, 13),
       (1, 14),
       (2, 15),
       (3, 16),
       (4, 17),
       (5, 18),
       (1, 19),
       (2, 20);


INSERT INTO trainings (trainee_id, trainer_id, type_id, name, date, duration)
VALUES (1, 1, 3, 'Flexibility Flow', '2024-01-01', 60),
       (1, 2, 4, 'Balance Harmony', '2024-01-03', 45),
       (1, 3, 5, 'Endurance Challenge', '2024-01-05', 30),
       (1, 4, 6, 'Power Boost', '2024-01-07', 50),
       (1, 5, 7, 'Agility Sprint', '2024-01-09', 40),

       (2, 6, 8, 'Core Crusher', '2024-01-02', 60),
       (2, 7, 9, 'Stamina Builder', '2024-01-04', 45),
       (2, 8, 10, 'Mindful Movement', '2024-01-06', 30),
       (2, 9, 1, 'Strength Master', '2024-01-08', 50),
       (2, 10, 2, 'Cardio Burner', '2024-01-10', 40),

       (3, 1, 3, 'Flexible Warrior', '2024-01-11', 60),
       (3, 2, 4, 'Balanced Act', '2024-01-13', 45),
       (3, 3, 5, 'Endurance Test', '2024-01-15', 30),
       (3, 4, 6, 'Power Hour', '2024-01-17', 50),
       (3, 5, 7, 'Agility Run', '2024-01-19', 40),

       (4, 6, 8, 'Core Blitz', '2024-01-20', 60),
       (4, 7, 9, 'Stamina Surge', '2024-01-22', 45),
       (4, 8, 10, 'Mindful Mastery', '2024-01-24', 30),
       (4, 9, 1, 'Strength Power', '2024-01-26', 50),
       (4, 10, 2, 'Cardio Quest', '2024-01-28', 40),

       (5, 1, 3, 'Flexi Fit', '2024-01-29', 60),
       (5, 2, 4, 'Balance Builder', '2024-01-31', 45),
       (5, 3, 5, 'Endurance Epic', '2024-02-02', 30),
       (5, 4, 6, 'Power Play', '2024-02-04', 50),
       (5, 5, 7, 'Agility Adventure', '2024-02-06', 40);

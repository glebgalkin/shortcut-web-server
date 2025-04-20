CREATE TABLE IF NOT EXISTS VisitRaw
(
    visit_info TEXT
);

INSERT INTO VisitRaw
VALUES ('Иванов И.И., ул. Ленина 10, +123456789; 2024-11-12; Смирнов А.А.:Кардиолог; Аспирин/100мг/30'),
       ('Петрова А.А., ул. Мира 5, +987654321; 2024-11-13; Козлова Н.Н.:Терапевт; Парацетамол/500мг/20,Цетрин/10мг/10'),
       ('Сидоров Б.Б., ул. Гагарина 22, +192837465; 2024-11-14; Смирнов А.А.:Кардиолог; Валидол/60мг/10'),
       ('Петрова А.А., ул. Мира 5, +987654321; 2024-12-01; Козлова Н.Н.:Терапевт; Парацетамол/500мг/10'),
       ('Иванов И.И., ул. Ленина 10, +123456789; 2024-12-02; Смирнов А.А.:Кардиолог; Аспирин/100мг/20,Валидол/60мг/10');


# Task 1 - Convert to 1NF
CREATE TABLE IF NOT EXISTS Visits1NF
(
    visit_id         INTEGER PRIMARY KEY,
    customer_name    VARCHAR(50),
    customer_address VARCHAR(100),
    customer_phone   VARCHAR(10),
    visit_date       DATE,
    doctor_name      VARCHAR(50),
    doctor_type      VARCHAR(20),
    medication       VARCHAR(20),
    dose_mg          INTEGER,
    amount           INTEGER
);
DESCRIBE Visits1NF;

INSERT INTO Visits1NF
VALUES (1, 'Иванов И.И.', 'ул. Ленина 10', '+123456789', '2024-11-12', 'Смирнов А.А', 'Кардиолог', 'Аспирин', '100',
        '30'),
       (2, 'Петрова А.А.', 'ул. Мира 5', '+987654321', '2024-11-13', 'Козлова Н.Н', 'Терапевт', 'Парацетамол', '500',
        '20'),
       (3, 'Петрова А.А.', 'ул. Мира 5', '+987654321', '2024-11-13', 'Козлова Н.Н', 'Терапевт', 'Цетрин', '10', '10'),
       (4, 'Сидоров Б.Б.', 'ул. Гагарина 22', '+192837465', '2024-11-14', 'Смирнов А.А.', 'Кардиолог', 'Валидол', '60',
        '10'),
       (5, 'Петрова А.А.', 'ул. Мира 5', '+987654321', '2024-12-01', 'Козлова Н.Н.', 'Терапевт', 'Парацетамол', '500',
        '10'),
       (6, 'Иванов И.И.', 'ул. Ленина 10', '+123456789', '2024-12-02', 'Смирнов А.А.', 'Кардиолог', 'Аспирин', '100',
        '20'),
       (7, 'Иванов И.И.', 'ул. Ленина 10', '+123456789', '2024-12-02', 'Смирнов А.А.', 'Кардиолог', 'Валидол', '60',
        '10');

SELECT *
FROM Visits1NF;


# Task 2
CREATE TABLE IF NOT EXISTS Patients
(
    id      INTEGER PRIMARY KEY,
    name    VARCHAR(50),
    address VARCHAR(100),
    phone   CHAR(10)
);

INSERT INTO Patients
VALUES (1, 'Иванов И.И.', 'ул. Ленина 10', '+123456789'),
       (2, 'Петрова А.А.', 'ул. Мира 5', '+987654321'),
       (3, 'Сидоров Б.Б.', 'ул. Гагарина ', '+192837465');

CREATE TABLE IF NOT EXISTS Doctors
(
    id        INTEGER PRIMARY KEY,
    name      VARCHAR(50),
    job_title VARCHAR(20)
);

INSERT INTO Doctors
VALUES (1, 'Смирнов А.А', 'Кардиолог'),
       (2, 'Козлова Н.Н', 'Терапевт');

CREATE TABLE IF NOT EXISTS Medications
(
    id   INTEGER PRIMARY KEY,
    name VARCHAR(20)
);

INSERT INTO Medications
VALUES (1, 'Аспирин'),
       (2, 'Парацетамол'),
       (3, 'Цетрин'),
       (4, 'Валидол');

CREATE TABLE IF NOT EXISTS Prescriptions
(
    id            INTEGER PRIMARY KEY,
    patient_id    INTEGER,
    medication_id INTEGER,
    dose_mg       INTEGER,
    amount        INTEGER,
    FOREIGN KEY (patient_id) REFERENCES Patients (id),
    FOREIGN KEY (medication_id) REFERENCES Medications (id)
);

INSERT INTO Prescriptions
VALUES (1, 1, 1, 100, 30),
       (2, 2, 2, 500, 20),
       (3, 2, 3, 10, 10),
       (4, 3, 4, 60, 10),
       (5, 2, 2, 500, 10),
       (6, 1, 1, 100, 20),
       (7, 1, 4, 60, 10);

CREATE TABLE IF NOT EXISTS Visits
(
    id              INTEGER PRIMARY KEY,
    date            DATE    NOT NULL,
    patient         INTEGER NOT NULL,
    doctor          INTEGER NOT NULL,
    prescription_id INTEGER,
    FOREIGN KEY (patient) REFERENCES Patients (id),
    FOREIGN KEY (doctor) REFERENCES Doctors (id),
    FOREIGN KEY (prescription_id) REFERENCES Prescriptions (id)
);

INSERT INTO Visits
VALUES (1, '2024-11-12', 1, 1, 1),
       (2, '2024-11-13', 2, 2, 2),
       (3, '2024-11-13', 2, 2, 3),
       (4, '2024-11-14', 3, 1, 4),
       (5, '2024-12-01', 2, 2, 5),
       (6, '2024-12-02', 1, 1, 6),
       (7, '2024-12-02', 1, 1, 7);


# Task 3 - Convert back to 1NF, expected result:
# (1, 'Иванов И.И.','ул. Ленина 10', '+123456789', '2024-11-12', 'Смирнов А.А', 'Кардиолог', 'Аспирин', '100', '30'),
# (2,'Петрова А.А.', 'ул. Мира 5', '+987654321','2024-11-13', 'Козлова Н.Н', 'Терапевт', 'Парацетамол', '500', '20'),
# (3,'Петрова А.А.', 'ул. Мира 5', '+987654321','2024-11-13', 'Козлова Н.Н', 'Терапевт', 'Цетрин', '10', '10'),
# (4, 'Сидоров Б.Б.', 'ул. Гагарина 22', '+192837465','2024-11-14',  'Смирнов А.А.', 'Кардиолог', 'Валидол', '60', '10'),
# (5, 'Петрова А.А.', 'ул. Мира 5', '+987654321', '2024-12-01', 'Козлова Н.Н.', 'Терапевт', 'Парацетамол', '500', '10'),
# (6, 'Иванов И.И.', 'ул. Ленина 10', '+123456789' ,'2024-12-02' ,'Смирнов А.А.', 'Кардиолог', 'Аспирин', '100', '20'),
# (7, 'Иванов И.И.', 'ул. Ленина 10', '+123456789' ,'2024-12-02' ,'Смирнов А.А.', 'Кардиолог', 'Валидол', '60', '10');

# Working case
SELECT v.id, p.name, p.address, p.phone, v.date, d.name AS doctor, d.job_title
FROM Visits v
         JOIN Patients p ON v.patient = p.id
         JOIN Doctors d ON v.doctor = d.id;

# TODO: TO FIX
SELECT v.id, p.name, p.address, p.phone, v.date, d.name AS doctor, d.job_title, m.name as medication, presc.dose_mg AS dose, presc.amount
FROM Visits v
         JOIN Patients p ON v.patient = p.id
         JOIN Doctors d ON v.doctor = d.id
         JOIN Prescriptions presc ON v.patient
         JOIN Medications m ON presc.medication_id = m.id;
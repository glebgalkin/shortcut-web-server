# Task 1
CREATE TABLE students
(
    id   INT PRIMARY KEY,
    name VARCHAR(100)
);
INSERT INTO students
VALUES (1, 'Аня'),
       (2, 'Борис'),
       (3, 'Вика');

CREATE TABLE grades
(
    student_id INT,
    course     VARCHAR(100),
    grade      INT
);

INSERT INTO grades
VALUES (1, 'Математика', 5),
       (2, 'Физика', 4),
       (3, 'Математика', 3),
       (3, 'История', 4);

# Solution:
SELECT st.name as student_name, g.grade
FROM students as st
         INNER JOIN grades g ON g.student_id = st.id
WHERE g.course = 'Математика';

# -----------------------------------------------------------------------

# Task 2
CREATE TABLE departments
(
    id   INT PRIMARY KEY,
    name VARCHAR(100)
);

INSERT INTO departments
VALUES (1, 'Разработка'),
       (2, 'Маркетинг');

CREATE TABLE employees
(
    id            INT PRIMARY KEY,
    name          VARCHAR(100),
    department_id INT
);

INSERT INTO employees
VALUES (1, 'Игорь', 1),
       (2, 'Юлия', 1),
       (3, 'Павел', 2),
       (4, 'Таня', 2);

CREATE TABLE projects
(
    id      INT PRIMARY KEY,
    name    VARCHAR(100),
    lead_id INT
);

INSERT INTO projects
VALUES (1, 'CRM-система', 1),
       (2, 'Landing Page', 4);

# Solution:
SELECT e.name as name, d.name as department, p.name as project_name
FROM employees as e
         INNER JOIN departments as d ON e.department_id = d.id
         LEFT JOIN projects as p ON e.id = p.lead_id
WHERE p.id IS NULL;

# -----------------------------------------------------------------------

# Task 3
CREATE TABLE clients (
                         id INT PRIMARY KEY,
                         name VARCHAR(100)
);

INSERT INTO clients VALUES
                        (1, 'ООО "Весна"'), (2, 'ЗАО "Техно"'), (3, 'ИП Иванов');

CREATE TABLE orders (
                        id INT PRIMARY KEY,
                        client_id INT,
                        product VARCHAR(100)
);

INSERT INTO orders VALUES
                       (1, 1, 'Принтер'),
                       (2, NULL, 'Ноутбук'),
                       (3, 4, 'Сканер');


SELECT clients.name, orders.product
FROM clients
         LEFT JOIN orders ON clients.id = orders.client_id

UNION

SELECT clients.name, orders.product
FROM clients
         RIGHT JOIN orders ON clients.id = orders.client_id;

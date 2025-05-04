-- =====================================
-- 📊 Структура базы данных и тестовые данные
-- =====================================

-- 1. Пользователи
CREATE TABLE users
(
    id      INT AUTO_INCREMENT PRIMARY KEY,
    name    TEXT,
    country TEXT
);
INSERT INTO users (name, country)
VALUES ('Alice', 'USA'),
       ('Bob', 'UK'),
       ('Carlos', 'Spain'),
       ('Diana', 'Germany'),
       ('Eva', 'USA'),
       ('Frank', 'France'),
       ('Grace', 'Italy'),
       ('Henry', 'Canada'),
       ('Isabel', 'Mexico'),
       ('Jack', 'UK');

-- 2. Заказы
CREATE TABLE orders
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    user_id    INT REFERENCES users (id),
    amount     DECIMAL,
    created_at DATE
);
INSERT INTO orders (user_id, amount, created_at)
VALUES (1, 100.0, '2023-12-01'),
       (2, 150.0, '2023-12-03'),
       (1, 200.0, '2024-01-15'),
       (3, 50.0, '2024-02-10'),
       (4, 300.0, '2024-02-12'),
       (5, 120.0, '2024-03-01'),
       (6, 180.0, '2024-03-10'),
       (7, 75.0, '2024-04-01'),
       (8, 90.0, '2024-04-15'),
       (9, 60.0, '2024-04-20');

-- 3. Продукты
CREATE TABLE products
(
    id    INT AUTO_INCREMENT PRIMARY KEY,
    name  TEXT,
    price DECIMAL
);
INSERT INTO products (name, price)
VALUES ('Laptop', 1000),
       ('Mouse', 20),
       ('Keyboard', 50),
       ('Monitor', 300),
       ('USB Cable', 10),
       ('Webcam', 80),
       ('Headphones', 150);

-- 4. Состав заказов
CREATE TABLE order_items
(
    order_id   INT REFERENCES orders (id),
    product_id INT REFERENCES products (id),
    quantity   INT
);
INSERT INTO order_items (order_id, product_id, quantity)
VALUES (1, 1, 1),
       (1, 2, 2),
       (2, 3, 1),
       (3, 4, 1),
       (4, 2, 1),
       (4, 5, 3),
       (5, 6, 2),
       (6, 7, 1),
       (7, 3, 1),
       (8, 1, 1),
       (9, 2, 2),
       (10, 4, 1);

-- 5. Категории
CREATE TABLE categories
(
    id    INT AUTO_INCREMENT PRIMARY KEY,
    name TEXT
);
INSERT INTO categories (name)
VALUES ('Electronics'),
       ('Accessories'),
       ('Peripherals');

-- 6. Привязка товаров к категориям
CREATE TABLE product_categories
(
    product_id  INT REFERENCES products (id),
    category_id INT REFERENCES categories (id)
);
INSERT INTO product_categories (product_id, category_id)
VALUES (1, 1),
       (2, 2),
       (3, 2),
       (4, 1),
       (5, 2),
       (6, 3),
       (7, 3);

-- 7. Возвраты
CREATE TABLE returns
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT REFERENCES orders (id),
    reason   TEXT
);
INSERT INTO returns (order_id, reason)
VALUES (2, 'Damaged'),
       (4, 'Changed mind'),
       (5, 'Wrong item'),
       (9, 'Customer canceled');

-- =====================================
-- 🧠 SQL задачи (только формулировки, без решений)
-- =====================================

-- Задача 1 — UNION
-- Найди список всех уникальных стран, из которых были:
-- 1) пользователи, сделавшие заказы (таблицы users, orders),
-- 2) пользователи, чьи заказы были возвращены (users, orders, returns).


-- Задача 2 — UNION ALL
-- Составь единый список всех операций двух типов:
-- - покупки (из таблицы orders),
-- - возвраты (из таблицы returns).
-- Выведи ID заказа, дату (created_at из orders), сумму (amount), тип операции ('purchase' или 'return'),
-- и причину (reason — только для возвратов).

-- Задача 3 — Подзапрос с агрегатной функцией
-- Выведи имена пользователей, чья суммарная сумма заказов превышает
-- среднюю сумму заказов по всем пользователям.

-- Задача 4 — Подзапрос в SELECT
-- Покажи список всех заказов с количеством различных товаров в каждом из них.

-- Задача 5 — LATERAL JOIN (PostgreSQL only)
-- Для каждого продукта выведи его название, цену и название категории
-- с минимальным id, к которой он относится.

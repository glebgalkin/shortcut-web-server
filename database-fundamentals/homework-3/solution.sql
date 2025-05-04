-- Задача 1 — UNION
-- Найди список всех уникальных стран, из которых были:
-- 1) пользователи, сделавшие заказы (таблицы users, orders),
SELECT DISTINCT users.country
FROM users
JOIN orders ON orders.user_id = users.id;

-- 2) пользователи, чьи заказы были возвращены (users, orders, returns).
SELECT DISTINCT users.country
FROM users
JOIN orders ON orders.user_id = users.id
JOIN returns ON returns.order_id = orders.id;

-- Задача 2 — UNION ALL
-- Составь единый список всех операций двух типов:
-- - покупки (из таблицы orders),
-- - возвраты (из таблицы returns).
-- Выведи ID заказа, дату (created_at из orders), сумму (amount), тип операции ('purchase' или 'return'),
-- и причину (reason — только для возвратов).
SELECT o.id, o.created_at, o.amount,
CASE WHEN returns.id IS NULL THEN 'purchase' else 'return' END as purchase_status, returns.reason
FROM orders as o
LEFT JOIN returns ON o.id = returns.order_id;

-- Задача 3 — Подзапрос с агрегатной функцией
-- Выведи имена пользователей, чья суммарная сумма заказов превышает
-- среднюю сумму заказов по всем пользователям.

SELECT users.id, users.name, SUM(orders.amount) as orders_sum
FROM users
JOIN orders ON users.id = orders.user_id
GROUP BY users.id, users.name;


SELECT AVG(orders.amount)
FROM orders;

SELECT users.name, order_sum.orders_sum, avg_order_amount.avg_amount
FROM users
JOIN (
    SELECT users.id, users.name, SUM(orders.amount) as orders_sum
    FROM users
             JOIN orders ON users.id = orders.user_id
    GROUP BY users.id, users.name
) order_sum on users.id = order_sum.id
JOIN (
    SELECT AVG(orders.amount) as avg_amount
    FROM orders
) avg_order_amount
WHERE order_sum.orders_sum > avg_order_amount.avg_amount;

-- Задача 4 — Подзапрос в SELECT
-- Покажи список всех заказов с количеством различных товаров в каждом из них.
SELECT orders.id, COUNT(order_items.product_id)
FROM orders
JOIN order_items ON orders.id = order_items.order_id
GROUP BY orders.id;

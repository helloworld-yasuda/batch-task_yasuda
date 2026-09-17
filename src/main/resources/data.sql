INSERT IGNORE INTO subscriptions (id, user_id, product_id, quantity, next_order_date, status)
VALUES (1, 1, 100, 2, '2026-07-06', 'ACTIVE');

INSERT IGNORE INTO subscriptions (id, user_id, product_id, quantity, next_order_date, status)
VALUES (2, 2, 200, 1, '2026-08-01', 'ACTIVE');

INSERT IGNORE INTO subscriptions (id, user_id, product_id, quantity, next_order_date, status)
VALUES (3, 3, 300, 1, '2026-07-06', 'INACTIVE');

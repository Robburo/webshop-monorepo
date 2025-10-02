-- Users
INSERT INTO users (username, email, password, role)
VALUES ('admin', 'admin@example.com', '$2a$12$OC5YaGmrKQO.PgXAwGXjRu4KiZkls71gw.PX8hCMoT5IKgwOrop0m', 'ROLE_ADMIN'),
       ('alice', 'alice@example.com', '$2a$10$Dow1X3VWM2cnUZHnS14GjeUNj7tuPjVRbiTWnWGWp2TASnQghz3n6', 'ROLE_USER'),
       ('bob', 'bob@example.com', '$2a$10$Dow1X3VWM2cnUZHnS14GjeUNj7tuPjVRbiTWnWGWp2TASnQghz3n6', 'ROLE_USER'),
       ('charlie', 'charlie@example.com', '$2a$10$Dow1X3VWM2cnUZHnS14GjeUNj7tuPjVRbiTWnWGWp2TASnQghz3n6', 'ROLE_ADMIN');

-- Categories
INSERT INTO categories (name)
VALUES ('Electronics'),
       ('Books'),
       ('Clothing'),
       ('Home & Kitchen'),
       ('Sports'),
       ('Toys');

-- Products
INSERT INTO products (name, description, price, stock, category_id)
VALUES ('Smartphone', 'Latest model smartphone', 899.99, 50, 1),
       ('Laptop', 'Lightweight ultrabook', 1200.00, 30, 1),
       ('Headphones', 'Noise cancelling headphones', 199.99, 100, 1),
       ('Novel A', 'Fiction bestseller', 15.99, 200, 2),
       ('Cookbook', 'Healthy recipes', 25.00, 150, 2),
       ('T-Shirt', 'Cotton, size M', 12.99, 500, 3),
       ('Jeans', 'Denim blue jeans', 45.00, 300, 3),
       ('Coffee Maker', 'Automatic espresso machine', 250.00, 20, 4),
       ('Blender', 'High-speed blender', 90.00, 40, 4),
       ('Basketball', 'Official size', 30.00, 80, 5),
       ('Tennis Racket', 'Lightweight racket', 120.00, 60, 5),
       ('Board Game', 'Family board game', 35.00, 70, 6),
       ('Action Figure', 'Collectible toy', 20.00, 120, 6);

-- Cart Items
INSERT INTO cart_items (user_id, product_id, quantity)
VALUES (1, 1, 2),
       (1, 4, 1),
       (2, 2, 1),
       (2, 6, 3),
       (3, 3, 1);

-- Orders
INSERT INTO orders (user_id, created_at, status, recipient_name, street, postal_code, city, country)
VALUES
    (1, NOW() - INTERVAL '5 days', 'PAID', 'Ola Nordmann', 'Karl Johans gate 1', '0154', 'Oslo', 'Norge'),
    (2, NOW() - INTERVAL '3 days', 'SHIPPED', 'Kari Nordmann', 'Dronningens gate 5', '7011', 'Trondheim', 'Norge'),
    (1, NOW() - INTERVAL '1 day', 'PENDING', 'Ola Nordmann', 'Storgata 10', '9008', 'Tromsø', 'Norge');

-- Order Items
INSERT INTO order_items (order_id, product_id, quantity, price)
VALUES (1, 1, 1, 899.99),
       (1, 4, 2, 15.99),
       (2, 2, 1, 1200.00),
       (2, 6, 2, 12.99),
       (3, 3, 1, 199.99);
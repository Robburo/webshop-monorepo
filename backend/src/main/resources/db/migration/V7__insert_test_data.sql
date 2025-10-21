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
VALUES
    -- (category_id = 1)
    ('Smartphone', 'Latest model smartphone', 899.99, 50, 1),
    ('Laptop', 'Lightweight ultrabook', 1200.00, 30, 1),
    ('Headphones', 'Noise cancelling headphones', 199.99, 100, 1),
    ('Tablet', '10-inch Android tablet', 349.00, 60, 1),
    ('Smartwatch', 'Fitness tracking smartwatch', 249.00, 70, 1),
    ('Wireless Mouse', 'Ergonomic wireless mouse', 29.99, 150, 1),
    ('Mechanical Keyboard', 'RGB backlit mechanical keyboard', 99.99, 80, 1),
    ('Monitor', '27-inch 4K monitor', 329.99, 40, 1),
    ('External SSD', '1TB USB-C SSD', 149.00, 90, 1),
    ('Bluetooth Speaker', 'Portable waterproof speaker', 79.99, 110, 1),
    ('Gaming Console', 'Next-gen gaming console', 499.00, 25, 1),
    ('Webcam', '1080p HD webcam', 59.99, 130, 1),

    -- (category_id = 2)
    ('Novel A', 'Fiction bestseller', 15.99, 200, 2),
    ('Cookbook', 'Healthy recipes', 25.00, 150, 2),
    ('Science Book', 'Astrophysics for beginners', 19.99, 120, 2),
    ('Thriller Novel', 'Suspenseful crime story', 14.50, 180, 2),
    ('Fantasy Epic', 'Book one in a fantasy saga', 29.99, 90, 2),
    ('History Book', 'World history overview', 22.50, 130, 2),
    ('Self Help Guide', 'Improve productivity and focus', 18.00, 140, 2),
    ('Children Story', 'Illustrated bedtime stories', 12.00, 160, 2),

    -- (category_id = 3)
    ('T-Shirt', 'Cotton, size M', 12.99, 500, 3),
    ('Jeans', 'Denim blue jeans', 45.00, 300, 3),
    ('Hoodie', 'Soft fleece hoodie', 39.99, 250, 3),
    ('Jacket', 'Waterproof outdoor jacket', 89.99, 100, 3),
    ('Sneakers', 'Casual running shoes', 59.99, 200, 3),
    ('Socks Pack', '5-pair cotton socks', 14.99, 400, 3),
    ('Cap', 'Adjustable baseball cap', 19.99, 250, 3),
    ('Scarf', 'Winter wool scarf', 24.99, 180, 3),
    ('Gloves', 'Thermal gloves', 19.99, 160, 3),
    ('Shorts', 'Sports shorts', 22.50, 210, 3),
    ('Belt', 'Leather belt', 29.99, 140, 3),
    ('Polo Shirt', 'Slim fit polo', 34.99, 190, 3),

    -- (category_id = 4)
    ('Coffee Maker', 'Automatic espresso machine', 250.00, 20, 4),
    ('Blender', 'High-speed blender', 90.00, 40, 4),
    ('Toaster', '4-slice toaster', 39.99, 60, 4),
    ('Microwave Oven', '800W compact microwave', 120.00, 35, 4),
    ('Vacuum Cleaner', 'Cordless stick vacuum', 180.00, 50, 4),
    ('Air Fryer', 'Oil-free digital air fryer', 110.00, 70, 4),
    ('Dishwasher', 'Energy efficient model', 499.00, 15, 4),
    ('Washing Machine', 'Front-load washing machine', 699.00, 10, 4),
    ('Refrigerator', 'Double-door fridge', 899.00, 8, 4),
    ('Electric Kettle', '1.7L stainless steel kettle', 29.99, 90, 4),

    -- (category_id = 5)
    ('Basketball', 'Official size', 30.00, 80, 5),
    ('Tennis Racket', 'Lightweight racket', 120.00, 60, 5),
    ('Football', 'Standard size football', 25.00, 100, 5),
    ('Yoga Mat', 'Non-slip exercise mat', 35.00, 120, 5),
    ('Running Shoes', 'Breathable mesh running shoes', 75.00, 150, 5),
    ('Gym Bag', 'Spacious duffel bag', 49.99, 90, 5),
    ('Water Bottle', 'Insulated stainless steel bottle', 19.99, 200, 5),
    ('Resistance Bands', 'Set of 5 workout bands', 24.99, 170, 5),
    ('Cycling Helmet', 'Adjustable bike helmet', 59.99, 60, 5),
    ('Jump Rope', 'Adjustable speed rope', 12.99, 180, 5),

    -- (category_id = 6)
    ('Board Game', 'Family board game', 35.00, 70, 6),
    ('Action Figure', 'Collectible toy', 20.00, 120, 6),
    ('Puzzle 1000pcs', 'Scenic landscape puzzle', 25.00, 140, 6),
    ('RC Car', 'Remote controlled racing car', 85.00, 60, 6),
    ('Lego Set', 'Building blocks kit', 59.99, 100, 6),
    ('Doll House', 'Wooden doll house', 75.00, 50, 6),
    ('Toy Train', 'Battery-powered train set', 49.99, 80, 6),
    ('Card Game', 'Fast-paced family game', 15.99, 130, 6),
    ('Drone', 'Mini quadcopter drone', 89.99, 40, 6),
    ('Plush Bear', 'Soft stuffed animal', 18.99, 200, 6);

-- Cart Items
INSERT INTO cart_items (user_id, product_id, quantity)
VALUES (1, 1, 2),
       (1, 4, 1),
       (2, 2, 1),
       (2, 6, 3),
       (3, 3, 1);

-- Orders
INSERT INTO orders (user_id, created_at, status, recipient_name, street, postal_code, city, country)
VALUES (1, NOW() - INTERVAL '5 days', 'PAID', 'Ola Nordmann', 'Karl Johans gate 1', '0154', 'Oslo', 'Norge'),
       (2, NOW() - INTERVAL '3 days', 'SHIPPED', 'Kari Nordmann', 'Dronningens gate 5', '7011', 'Trondheim', 'Norge'),
       (1, NOW() - INTERVAL '1 day', 'PENDING', 'Ola Nordmann', 'Storgata 10', '9008', 'Tromsø', 'Norge');

-- Order Items
INSERT INTO order_items (order_id, product_id, quantity, price)
VALUES (1, 1, 1, 899.99),
       (1, 4, 2, 15.99),
       (2, 2, 1, 1200.00),
       (2, 6, 2, 12.99),
       (3, 3, 1, 199.99);
INSERT INTO users (username, password, role, email)
VALUES ('admin', '{noop}admin123', 'ROLE_ADMIN', 'admin@example.com');

INSERT INTO categories (name)
VALUES ('Electronics'),
       ('Books'),
       ('Clothing');

INSERT INTO products (name, description, price, stock, category_id)
VALUES ('Laptop', 'Powerful gaming laptop', 1500.00, 5, 1),
       ('Novel', 'Bestselling book', 19.99, 20, 2),
       ('T-Shirt', 'Cotton t-shirt', 9.99, 50, 3);
CREATE TABLE products (
                         id BIGSERIAL PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         description TEXT,
                         price NUMERIC(10,2) NOT NULL,
                         stock INT NOT NULL,
                         category_id BIGINT REFERENCES categories(id)
);
CREATE TABLE order_items (
                            id BIGSERIAL PRIMARY KEY,
                            order_id BIGINT REFERENCES orders(id),
                            product_id BIGINT REFERENCES products(id),
                            quantity INT NOT NULL,
                            price NUMERIC(10,2) NOT NULL
);
CREATE TABLE cart_items (
                           id BIGSERIAL PRIMARY KEY,
                           user_id BIGINT REFERENCES users(id),
                           product_id BIGINT REFERENCES products(id),
                           quantity INT NOT NULL
);
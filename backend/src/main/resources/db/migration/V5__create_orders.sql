CREATE TABLE orders (
                        id BIGSERIAL PRIMARY KEY,
                        user_id BIGINT REFERENCES users(id),
                        created_at TIMESTAMP NOT NULL DEFAULT NOW(),
                        status VARCHAR(50) NOT NULL
);
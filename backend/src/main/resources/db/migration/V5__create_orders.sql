CREATE TABLE orders (
                        id BIGSERIAL PRIMARY KEY,
                        user_id BIGINT NOT NULL REFERENCES users(id),
                        created_at TIMESTAMP NOT NULL DEFAULT NOW(),
                        status VARCHAR(50) NOT NULL,
                        recipient_name VARCHAR(255) NOT NULL,
                        street VARCHAR(255) NOT NULL,
                        postal_code VARCHAR(20) NOT NULL,
                        city VARCHAR(100) NOT NULL,
                        country VARCHAR(100) NOT NULL
);

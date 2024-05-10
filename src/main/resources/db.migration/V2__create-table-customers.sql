CREATE TABLE customers (
    id BINARY(16) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    birth_date DATE NOT NULL,
    age INTEGER NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(14) UNIQUE,
    cpf VARCHAR(14) UNIQUE,
    address_id BIGINT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    active BOOLEAN,
    FOREIGN KEY (address_id) REFERENCES addresses(id)
);

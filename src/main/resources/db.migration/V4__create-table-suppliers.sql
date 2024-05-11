CREATE TABLE suppliers (
    id BINARY(16) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    cnpj VARCHAR(26) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(255),
    site VARCHAR(255),
    address_id BIGINT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    active BOOLEAN,
    FOREIGN KEY (address_id) REFERENCES addresses(id)
);

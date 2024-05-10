CREATE TABLE employees (
    id BINARY(16) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    birth_date DATE NOT NULL,
    age INTEGER NOT NULL,
    phone VARCHAR(255),
    salary DECIMAL(19, 2) NOT NULL,
    role VARCHAR(50),
    email VARCHAR(255),
    cpf VARCHAR(14),
    address_id BIGINT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    active BOOLEAN,
    FOREIGN KEY (address_id) REFERENCES address(id)
);

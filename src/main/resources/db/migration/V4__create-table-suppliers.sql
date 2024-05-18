CREATE TABLE IF NOT EXISTS suppliers (
    id BINARY(16) PRIMARY KEY,
    corporate_name VARCHAR(255) NOT NULL,
    trade_name VARCHAR(255),
    cnpj VARCHAR(26) NOT NULL,
    phone VARCHAR(20),
    registration_status VARCHAR(255),
    email VARCHAR(255),
    legal_nature VARCHAR(255),
    address_id BIGINT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    active BOOLEAN,
    FOREIGN KEY (address_id) REFERENCES addresses(id)
) engine=InnoDB;

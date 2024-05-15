CREATE TABLE service_orders (
    id BINARY(16) PRIMARY KEY,
    customer_id BINARY(16) REFERENCES customers(id),
    employee_id BINARY(16) REFERENCES employees(id),
    description VARCHAR(255),
    service_order_status ENUM("PENDING", "IN_PROGRESS", "COMPLETED", "CANCELED"),
    labor_price DECIMAL(19, 2),
    cost DECIMAL(19, 2),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,

    FOREIGN KEY (customer_id) REFERENCES customers(id),
    FOREIGN KEY (employee_id) REFERENCES employees(id)
);
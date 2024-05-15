CREATE TABLE service_order_products (
    service_order_id BINARY(16) REFERENCES service_orders(id),
    product_id BIGINT REFERENCES products(id),
    PRIMARY KEY (service_order_id, product_id)
);
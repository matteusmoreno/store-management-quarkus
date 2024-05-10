CREATE TABLE addresses (
    id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    zipcode VARCHAR(9) NOT NULL,
    street VARCHAR(255),
    city VARCHAR(255),
    neighborhood VARCHAR(255),
    state CHAR(2)
);

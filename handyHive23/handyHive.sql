-- Drop in correct dependency order
DROP VIEW IF EXISTS provider_review_stats;
DROP TABLE IF EXISTS review;
DROP TABLE IF EXISTS service;
DROP TABLE IF EXISTS provider;
DROP TABLE IF EXISTS customer;

-- Provider table
CREATE TABLE provider (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(50),
    address VARCHAR(255),
    expertise VARCHAR(255),
    availability VARCHAR(255),
    pricing_details VARCHAR(255)
);

ALTER TABLE provider ADD COLUMN business_name VARCHAR(255);

CREATE TABLE service (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2),
    availability VARCHAR(255),
    provider_id BIGINT NOT NULL,
    FOREIGN KEY (provider_id) REFERENCES provider(id)
);

CREATE TABLE customer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    phone VARCHAR(50),
    address VARCHAR(255)
);

CREATE TABLE review (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    rating INT NOT NULL,
    comment TEXT,
    review_date DATE,
    customer_id BIGINT NOT NULL,
    service_id BIGINT NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer(id),
    FOREIGN KEY (service_id) REFERENCES service(id)
);

CREATE VIEW provider_review_stats AS
SELECT
    s.id AS service_id,
    s.title AS service_title,
    AVG(r.rating) AS average_rating,
    COUNT(r.id) AS review_count,
    s.provider_id
FROM review r
JOIN service s ON r.service_id = s.id
GROUP BY s.id, s.title, s.provider_id;

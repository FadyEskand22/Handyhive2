-- HandyHive Database Schema
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



ALTER TABLE provider
ADD COLUMN sun_start VARCHAR(255),
ADD COLUMN sun_end VARCHAR(255),
ADD COLUMN mon_start VARCHAR(255),
ADD COLUMN mon_end VARCHAR(255),
ADD COLUMN tue_start VARCHAR(255),
ADD COLUMN tue_end VARCHAR(255),
ADD COLUMN wed_start VARCHAR(255),
ADD COLUMN wed_end VARCHAR(255),
ADD COLUMN thu_start VARCHAR(255),
ADD COLUMN thu_end VARCHAR(255),
ADD COLUMN fri_start VARCHAR(255),
ADD COLUMN fri_end VARCHAR(255),
ADD COLUMN sat_start VARCHAR(255),
ADD COLUMN sat_end VARCHAR(255);

CREATE TABLE service_like (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    service_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    liked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (service_id) REFERENCES service(id),
    FOREIGN KEY (customer_id) REFERENCES customer(id)
);

CREATE TABLE service_comment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    service_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    commented_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (service_id) REFERENCES service(id),
    FOREIGN KEY (customer_id) REFERENCES customer(id)
);


ALTER TABLE review DROP FOREIGN KEY review_ibfk_1;


ALTER TABLE review
ADD CONSTRAINT review_ibfk_1
FOREIGN KEY (customer_id)
REFERENCES customer(id)
ON DELETE CASCADE;

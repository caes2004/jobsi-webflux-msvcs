CREATE TABLE IF NOT EXISTS users (
                       id VARCHAR(36) PRIMARY KEY,
                       document_number VARCHAR(50),
                       first_name VARCHAR(100),
                       last_name VARCHAR(100),
                       name VARCHAR(100),
                       email VARCHAR(150) UNIQUE,
                       password VARCHAR(255),
                       phone_number VARCHAR(30),
                       birth_date DATE,
                       is_active BOOLEAN,
                       role VARCHAR(50),
                       gender VARCHAR(20)
);

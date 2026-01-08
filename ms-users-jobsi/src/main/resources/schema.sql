CREATE TABLE IF NOT EXISTS users (
                       id VARCHAR(36) PRIMARY KEY,
                       document_number VARCHAR(50) NOT NULL UNIQUE,
                       first_name VARCHAR(100) NOT NULL,
                       last_name VARCHAR(100) ,
                       name VARCHAR(100) NOT NULL,
                       email VARCHAR(150) UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       phone_number VARCHAR(30) NOT NULL,
                       birth_date DATE,
                       is_active BOOLEAN,
                       role VARCHAR(50),
                       gender VARCHAR(20)
);

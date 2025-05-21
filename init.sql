-- Criação dos bancos de dados
SELECT 'Creating databases...' AS log;
CREATE DATABASE IF NOT EXISTS clientdb;
CREATE DATABASE IF NOT EXISTS productdb;
CREATE DATABASE IF NOT EXISTS stockdb;
CREATE DATABASE IF NOT EXISTS orderdb;
CREATE DATABASE IF NOT EXISTS paymentdb;

-- Concessão de permissões para o usuário 'user' (já criado pelas variáveis de ambiente)
SELECT 'Granting privileges...' AS log;
GRANT ALL PRIVILEGES ON clientdb.* TO 'user'@'%';
GRANT ALL PRIVILEGES ON productdb.* TO 'user'@'%';
GRANT ALL PRIVILEGES ON stockdb.* TO 'user'@'%';
GRANT ALL PRIVILEGES ON orderdb.* TO 'user'@'%';
GRANT ALL PRIVILEGES ON paymentdb.* TO 'user'@'%';

-- Aplica as permissões
FLUSH PRIVILEGES;

-- Criação das tabelas no banco orderdb
SELECT 'Creating tables in orderdb...' AS log;
USE orderdb;

CREATE TABLE IF NOT EXISTS payment_details (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    card_number VARCHAR(255) NOT NULL,
    payment_method VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL
);
SELECT 'Created payment_details table' AS log;

CREATE TABLE IF NOT EXISTS orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    client_cpf VARCHAR(11) NOT NULL,
    payment_details_id BIGINT,
    product_quantity INT NOT NULL,
    product_sku VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL,
    stock_reserved BOOLEAN NOT NULL DEFAULT FALSE,
    total_amount DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (payment_details_id) REFERENCES payment_details(id)
);
SELECT 'Created orders table' AS log;
CREATE DATABASE IF NOT EXISTS gestion_tarjetas;
USE gestion_tarjetas;

CREATE TABLE cliente (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    identificacion VARCHAR(20) NOT NULL UNIQUE,
    correo VARCHAR(100) NOT NULL,
    primer_nombre VARCHAR(50) NOT NULL,
    segundo_nombre VARCHAR(50),
    primer_apellido VARCHAR(50) NOT NULL,
    segundo_apellido VARCHAR(50)
);

CREATE TABLE tarjeta_credito (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_tarjeta VARCHAR(20) NOT NULL UNIQUE,
    fecha_vencimiento VARCHAR(7) NOT NULL, 
    franquicia VARCHAR(20),
    estado VARCHAR(20) NOT NULL,
    cupo_total DECIMAL(12,2) NOT NULL,
    cupo_disponible DECIMAL(12,2) NOT NULL,
    cupo_utilizado DECIMAL(12,2),
    cliente_id BIGINT NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES cliente(id)
);

INSERT INTO cliente (identificacion, correo, primer_nombre, segundo_nombre, primer_apellido, segundo_apellido) VALUES
('1001234567', 'andrea.gomez@email.com', 'Andrea', 'Paola', 'Gómez', 'López'),
('1007654321', 'carlos.martinez@email.com', 'Carlos', NULL, 'Martínez', 'Suárez'),
('1012349876', 'luisa.fernandez@email.com', 'Luisa', 'Fernanda', 'Fernández', 'Ruiz'),
('1023456789', 'david.perez@email.com', 'David', NULL, 'Pérez', 'Castro'),
('1034567890', 'maria.torres@email.com', 'María', NULL, 'Torres', 'Vargas');


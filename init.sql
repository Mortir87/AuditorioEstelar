CREATE DATABASE IF NOT EXISTS teatro_proyecto;
USE teatro_proyecto;

CREATE TABLE IF NOT EXISTS usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    telefono VARCHAR(20),
    password VARCHAR(255) NOT NULL,
    rol ENUM('admin', 'cliente') DEFAULT 'cliente'
);

CREATE TABLE IF NOT EXISTS recinto (
    id_recinto INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    capacidad_total INT NOT NULL
);

CREATE TABLE IF NOT EXISTS zona (
    id_zona INT AUTO_INCREMENT PRIMARY KEY,
    id_recinto INT,
    nombre VARCHAR(50) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_recinto) REFERENCES recinto(id_recinto)
);

CREATE TABLE IF NOT EXISTS butaca (
    id_butaca INT AUTO_INCREMENT PRIMARY KEY,
    id_zona INT,
    fila INT NOT NULL,
    numero INT NOT NULL,
    FOREIGN KEY (id_zona) REFERENCES zona(id_zona),
    UNIQUE KEY unique_butaca (id_zona, fila, numero)
);

CREATE TABLE IF NOT EXISTS concierto (
    id_concierto INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    artista VARCHAR(200) NOT NULL,
    descripcion TEXT,
    cartel_url VARCHAR(500),
    precio_base DECIMAL(10,2) NOT NULL
);

CREATE TABLE IF NOT EXISTS sesion (
    id_sesion INT AUTO_INCREMENT PRIMARY KEY,
    id_concierto INT,
    id_recinto INT,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    FOREIGN KEY (id_concierto) REFERENCES concierto(id_concierto),
    FOREIGN KEY (id_recinto) REFERENCES recinto(id_recinto)
);

CREATE TABLE IF NOT EXISTS butaca_sesion (
    id_butaca INT NOT NULL,
    id_sesion INT NOT NULL,
    estado ENUM('DISPONIBLE', 'RESERVADA', 'VENDIDA') DEFAULT 'DISPONIBLE',
    PRIMARY KEY (id_butaca, id_sesion),
    FOREIGN KEY (id_butaca) REFERENCES butaca(id_butaca),
    FOREIGN KEY (id_sesion) REFERENCES sesion(id_sesion)
);

CREATE TABLE IF NOT EXISTS reserva (
    id_reserva INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT,
    id_sesion INT,
    fecha_reserva DATETIME DEFAULT CURRENT_TIMESTAMP,
    estado ENUM('pendiente', 'confirmada', 'cancelada') DEFAULT 'pendiente',
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
    FOREIGN KEY (id_sesion) REFERENCES sesion(id_sesion)
);

CREATE TABLE IF NOT EXISTS detalle_reserva (
    id_detalle INT AUTO_INCREMENT PRIMARY KEY,
    id_reserva INT,
    id_butaca INT,
    precio_pagado DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_reserva) REFERENCES reserva(id_reserva),
    FOREIGN KEY (id_butaca) REFERENCES butaca(id_butaca)
);

INSERT INTO recinto (nombre, capacidad_total) VALUES 
('Auditorio Estelar', 100);

INSERT INTO zona (id_recinto, nombre, precio) VALUES 
(1, 'Patio de Butacas', 50.00),
(1, 'Anfiteatro', 35.00);

INSERT INTO butaca (id_zona, fila, numero) VALUES 
(1, 1, 1), (1, 1, 2), (1, 1, 3), (1, 1, 4),
(1, 2, 1), (1, 2, 2), (1, 2, 3), (1, 2, 4),
(2, 1, 1), (2, 1, 2), (2, 1, 3), (2, 1, 4),
(2, 2, 1), (2, 2, 2), (2, 2, 3), (2, 2, 4);

INSERT INTO usuario (nombre, apellido, email, telefono, password, rol) VALUES 
('Admin', '', 'admin@teatro.com', '', 'admin123', 'admin');

INSERT INTO concierto (titulo, artista, descripcion, precio_base) VALUES 
('Noche de Gala', 'Orquesta Sinfónica', 'Concierto de gala con grandes clásicos', 60.00),
('Rock Nacional', 'Los Temas', 'Una noche de rock con los mejores', 45.00);

INSERT INTO sesion (id_concierto, id_recinto, fecha, hora) VALUES 
(1, 1, '2026-05-01', '20:00:00'),
(1, 1, '2026-05-02', '20:00:00'),
(2, 1, '2026-05-10', '22:00:00'),
(2, 1, '2026-05-11', '22:00:00');

INSERT INTO butaca_sesion (id_butaca, id_sesion, estado) 
SELECT b.id_butaca, s.id_sesion, 'DISPONIBLE'
FROM butaca b
CROSS JOIN sesion s;

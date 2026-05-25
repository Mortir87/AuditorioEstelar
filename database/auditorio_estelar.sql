CREATE DATABASE IF NOT EXISTS teatro_proyecto;
USE teatro_proyecto;

CREATE TABLE usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    telefono VARCHAR(20),
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    rol ENUM('cliente','admin') DEFAULT 'cliente'
);

CREATE TABLE concierto (
    id_concierto INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    descripcion TEXT,
    cartel_url VARCHAR(255)
);

CREATE TABLE sesion (
    id_sesion INT AUTO_INCREMENT PRIMARY KEY,
    id_concierto INT NOT NULL,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    FOREIGN KEY (id_concierto)
        REFERENCES concierto(id_concierto)
        ON DELETE CASCADE
);

CREATE TABLE zona (
    id_zona INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    precio DECIMAL(6,2) NOT NULL
);
CREATE TABLE butaca (
    id_butaca INT AUTO_INCREMENT PRIMARY KEY,
    id_zona INT NOT NULL,
    fila INT NOT NULL,
    numero INT NOT NULL,
    FOREIGN KEY (id_zona)
        REFERENCES zona(id_zona)
);
CREATE TABLE butaca_sesion (
    id_sesion INT NOT NULL,
    id_butaca INT NOT NULL,
    estado ENUM('DISPONIBLE','VENDIDA','RESERVADA','BLOQUEADA')
           DEFAULT 'DISPONIBLE',
    PRIMARY KEY (id_sesion, id_butaca),
    FOREIGN KEY (id_sesion)
        REFERENCES sesion(id_sesion)
        ON DELETE CASCADE,
    FOREIGN KEY (id_butaca)
        REFERENCES butaca(id_butaca)
        ON DELETE CASCADE
);
CREATE TABLE reserva (
    id_reserva INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    fecha_reserva DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario)
        REFERENCES usuario(id_usuario)
        ON DELETE CASCADE
);
CREATE TABLE reserva_butaca (
    id_reserva INT NOT NULL,
    id_sesion INT NOT NULL,
    id_butaca INT NOT NULL,
    PRIMARY KEY (id_reserva, id_sesion, id_butaca),
    FOREIGN KEY (id_reserva)
        REFERENCES reserva(id_reserva)
        ON DELETE CASCADE,
    FOREIGN KEY (id_sesion, id_butaca)
        REFERENCES butaca_sesion(id_sesion, id_butaca)
        ON DELETE CASCADE
);
CREATE TABLE venta (
    id_venta INT AUTO_INCREMENT PRIMARY KEY,
    id_reserva INT NOT NULL,
    fecha_venta DATETIME DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(8,2) NOT NULL,
    FOREIGN KEY (id_reserva)
        REFERENCES reserva(id_reserva)
        ON DELETE CASCADE
);
CREATE TABLE detalle_venta (
    id_detalle INT AUTO_INCREMENT PRIMARY KEY,
    id_venta INT NOT NULL,
    id_butaca INT NOT NULL,
    id_sesion INT NOT NULL,
    precio DECIMAL(6,2) NOT NULL,
    FOREIGN KEY (id_venta)
        REFERENCES venta(id_venta)
        ON DELETE CASCADE,
    FOREIGN KEY (id_sesion, id_butaca)
        REFERENCES butaca_sesion(id_sesion, id_butaca)
);

-- Datos

INSERT INTO zona (nombre, precio) VALUES
('Patio', 50.00),
('Anfiteatro', 30.00);


INSERT INTO butaca (id_butaca, id_zona, fila, numero) VALUES
-- PATIO DE BUTACAS
(1, 1, 1, 1),
(2, 1, 1, 2),
(3, 1, 1, 3),
(4, 1, 1, 4),

(5, 1, 2, 5),
(6, 1, 2, 6),
(7, 1, 2, 7),
(8, 1, 2, 8),

-- ANFITEATRO
(9, 2, 3, 9),
(10, 2, 3, 10),
(11, 2, 3, 11),
(12, 2, 3, 12),

(13, 2, 4, 13),
(14, 2, 4, 14),
(15, 2, 4, 15),
(16, 2, 4, 16);

-- CONCIERTOS

INSERT INTO concierto (titulo, descripcion, cartel_url) VALUES
('Gran Concierto Estelar',
 'Una noche mágica bajo las estrellas con artistas invitados',
 'https://images.unsplash.com/photo-1540039155733-5bb30b53aa14?auto=format&fit=crop&w=800&q=80'),

('Rock en el Auditorio',
 'Toda la energía del rock local en directo',
 'https://images.unsplash.com/photo-1501281668745-f7f57925c3b4?auto=format&fit=crop&w=800&q=80'),

('Festival de Jazz',
 'Los mejores exponentes del jazz nacional e internacional',
 'https://images.unsplash.com/photo-1511192336575-5a79af67a629?auto=format&fit=crop&w=800&q=80'),

('Noche Sinfónica',
 'Orquesta sinfónica interpretando grandes clásicos',
 'https://images.unsplash.com/photo-1465847899084-d164df4dedc6?auto=format&fit=crop&w=800&q=80'),

('Pop Live Tour',
 'Los éxitos del pop del momento en un espectáculo único',
 'https://images.unsplash.com/photo-1506157786151-b8491531f063?auto=format&fit=crop&w=800&q=80');

-- SESIONES FUTURAS
-- =========================

INSERT INTO sesion (
    id_concierto,
    fecha,
    hora
) VALUES

(1, DATE_ADD(CURDATE(), INTERVAL 3 DAY), '20:00:00'),
(1, DATE_ADD(CURDATE(), INTERVAL 10 DAY), '21:00:00'),

(2, DATE_ADD(CURDATE(), INTERVAL 5 DAY), '19:30:00'),
(2, DATE_ADD(CURDATE(), INTERVAL 15 DAY), '21:30:00'),

(3, DATE_ADD(CURDATE(), INTERVAL 7 DAY), '20:30:00'),
(3, DATE_ADD(CURDATE(), INTERVAL 18 DAY), '22:00:00'),

(4, DATE_ADD(CURDATE(), INTERVAL 12 DAY), '18:00:00'),
(4, DATE_ADD(CURDATE(), INTERVAL 20 DAY), '20:00:00'),

(5, DATE_ADD(CURDATE(), INTERVAL 14 DAY), '21:00:00'),
(5, DATE_ADD(CURDATE(), INTERVAL 25 DAY), '22:00:00');

-- BUTACAS_SESION
INSERT INTO butaca_sesion (
    id_sesion,
    id_butaca,
    estado
)
SELECT
    s.id_sesion,
    b.id_butaca,
    'DISPONIBLE'
FROM sesion s
CROSS JOIN butaca b;
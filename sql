DROP DATABASE IF EXISTS veterinaria_db_c;
CREATE DATABASE veterinaria_db_c
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE veterinaria_db_c;

CREATE TABLE clientes (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    telefono VARCHAR(30),
    email VARCHAR(120),
    direccion VARCHAR(150),
    activo BOOLEAN DEFAULT TRUE
);

CREATE TABLE mascotas (
    id_mascota INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    especie VARCHAR(50) NOT NULL,
    raza VARCHAR(80),
    edad INT,
    peso DECIMAL(5,2),
    id_cliente INT NOT NULL,

    CONSTRAINT fk_mascotas_clientes
    FOREIGN KEY (id_cliente)
    REFERENCES clientes(id_cliente)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
);

CREATE TABLE veterinarios (
    id_veterinario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    matricula VARCHAR(50) NOT NULL,
    especialidad VARCHAR(100),
    telefono VARCHAR(30),
    email VARCHAR(120)
);

CREATE TABLE turnos (
    id_turno INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    motivo VARCHAR(255),
    estado VARCHAR(50) DEFAULT 'pendiente', -- pendiente, confirmado, cancelado, atendido
    id_cliente INT NOT NULL,
    id_veterinario INT NOT NULL,
    id_mascota INT NOT NULL,

    CONSTRAINT fk_turnos_clientes
    FOREIGN KEY (id_cliente)
    REFERENCES clientes(id_cliente)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,

    CONSTRAINT fk_turnos_veterinarios
    FOREIGN KEY (id_veterinario)
    REFERENCES veterinarios(id_veterinario)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,

    
    CONSTRAINT fk_turnos_mascotas
    FOREIGN KEY (id_mascota)
    REFERENCES mascotas(id_mascota)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
);


INSERT INTO veterinarios 
(nombre, apellido, matricula, especialidad, telefono, email)
VALUES
('Carlos', 'Ramírez', 'MP-1234', 'Clínica General', '2239990000', 'carlos.ramirez@veterinaria.com'),
('Ana', 'López', 'MP-5678', 'Cirugía', '2237774444', 'ana.lopez@veterinaria.com');


INSERT INTO clientes 
(nombre, apellido, telefono, email, direccion)
VALUES
('Juan', 'Pérez', '2234567890', 'juan.perez@gmail.com', 'Av. Colón 1234'),
('María', 'Gómez', '2235558888', 'maria.gomez@gmail.com', 'San Martín 4500'),
('Lucía', 'Fernández', '2231112222', 'lucia.fernandez@gmail.com', 'Independencia 3000');

INSERT INTO mascotas
(nombre, especie, raza, edad, peso, id_cliente)
VALUES
('Firulais', 'Perro', 'Labrador', 5, 28.50, 1),
('Mishi', 'Gato', 'Siamés', 3, 4.20, 2),
('Toby', 'Perro', 'Caniche', 2, 6.80, 3),
('Luna', 'Gato', 'Mestizo', 1, 3.10, 1);


INSERT INTO turnos 
(fecha, hora, motivo, estado, id_cliente, id_veterinario, id_mascota)
VALUES
('2024-05-10', '10:00:00', 'Control de rutina y vacunas', 'confirmado', 1, 1, 1), 
('2024-05-10', '11:30:00', 'Problema estomacal', 'pendiente', 2, 1, 2),       
('2024-05-12', '16:00:00', 'Consulta pre-quirúrgica', 'confirmado', 3, 2, 3); 


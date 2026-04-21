-- -----------------------------------------------------
-- TABLA: veterinarios
-- -----------------------------------------------------
CREATE TABLE veterinarios (
    id_veterinario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    matricula VARCHAR(50) NOT NULL,
    especialidad VARCHAR(100),
    telefono VARCHAR(30),
    email VARCHAR(120)
);

-- -----------------------------------------------------
-- TABLA: turnos (Tabla intermedia)
-- -----------------------------------------------------
CREATE TABLE turnos (
    id_turno INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    motivo VARCHAR(255),
    estado VARCHAR(50) DEFAULT 'pendiente', -- pendiente, confirmado, cancelado, atendido
    id_cliente INT NOT NULL,
    id_veterinario INT NOT NULL,
    id_mascota INT NOT NULL,

    -- Clave foránea hacia clientes
    CONSTRAINT fk_turnos_clientes
    FOREIGN KEY (id_cliente)
    REFERENCES clientes(id_cliente)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,

    -- Clave foránea hacia veterinarios
    CONSTRAINT fk_turnos_veterinarios
    FOREIGN KEY (id_veterinario)
    REFERENCES veterinarios(id_veterinario)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,

    -- Clave foránea hacia mascotas
    CONSTRAINT fk_turnos_mascotas
    FOREIGN KEY (id_mascota)
    REFERENCES mascotas(id_mascota)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
);

-- -----------------------------------------------------
-- DATOS DE PRUEBA (INSERTS)
-- -----------------------------------------------------

-- Insertamos un par de veterinarios
INSERT INTO veterinarios 
(nombre, apellido, matricula, especialidad, telefono, email)
VALUES
('Carlos', 'Ramírez', 'MP-1234', 'Clínica General', '2239990000', 'carlos.ramirez@veterinaria.com'),
('Ana', 'López', 'MP-5678', 'Cirugía', '2237774444', 'ana.lopez@veterinaria.com');

-- Insertamos un par de turnos de prueba
-- Usamos los IDs de los clientes y mascotas que ya venían en tu script
INSERT INTO turnos 
(fecha, hora, motivo, estado, id_cliente, id_veterinario, id_mascota)
VALUES
('2024-05-10', '10:00:00', 'Control de rutina y vacunas', 'confirmado', 1, 1, 1), -- Juan lleva a Firulais con el Dr. Carlos
('2024-05-10', '11:30:00', 'Problema estomacal', 'pendiente', 2, 1, 2),        -- María lleva a Mishi con el Dr. Carlos
('2024-05-12', '16:00:00', 'Consulta pre-quirúrgica', 'confirmado', 3, 2, 3);    -- Lucía lleva a Toby con la Dra. Ana

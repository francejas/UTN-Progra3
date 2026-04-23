-- Conversión de SQLite a MySQL
-- Archivo origen: Copia de banco.db

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `credenciales`;
DROP TABLE IF EXISTS `cuentas`;
DROP TABLE IF EXISTS `usuarios`;

CREATE TABLE `usuarios` (
  `id_usuario` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(100) NOT NULL,
  `apellido` VARCHAR(100) NOT NULL,
  `dni` VARCHAR(20) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `fecha_creacion` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `uk_usuarios_dni` (`dni`),
  UNIQUE KEY `uk_usuarios_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `cuentas` (
  `id_cuenta` INT NOT NULL AUTO_INCREMENT,
  `id_usuario` INT NOT NULL,
  `tipo` ENUM('CAJA_AHORRO', 'CUENTA_CORRIENTE') NOT NULL,
  `saldo` DECIMAL(15,2) DEFAULT 0.00,
  `fecha_creacion` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_cuenta`),
  KEY `idx_cuentas_id_usuario` (`id_usuario`),
  CONSTRAINT `fk_cuentas_usuario`
    FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`)
    ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `credenciales` (
  `id_credencial` INT NOT NULL AUTO_INCREMENT,
  `id_usuario` INT NOT NULL,
  `username` VARCHAR(100) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `permiso` ENUM('CLIENTE', 'ADMINISTRADOR', 'GESTOR') NOT NULL,
  PRIMARY KEY (`id_credencial`),
  UNIQUE KEY `uk_credenciales_username` (`username`),
  KEY `idx_credenciales_id_usuario` (`id_usuario`),
  CONSTRAINT `fk_credenciales_usuario`
    FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`)
    ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `usuarios` (`id_usuario`, `nombre`, `apellido`, `dni`, `email`, `fecha_creacion`) VALUES
  (1, 'Juan', 'Pérez', '12345678', 'juan.perez@email.com', '2025-03-25 15:37:49'),
  (2, 'María', 'Gómez', '23456789', 'maria.gomez@email.com', '2025-03-25 15:37:49'),
  (3, 'Carlos', 'López', '34567890', 'carlos.lopez@email.com', '2025-03-25 15:37:49'),
  (4, 'Ana', 'Martínez', '45678901', 'ana.martinez@email.com', '2025-03-25 15:37:49'),
  (5, 'Pedro', 'Fernández', '56789012', 'pedro.fernandez@email.com', '2025-03-25 15:37:49');

INSERT INTO `cuentas` (`id_cuenta`, `id_usuario`, `tipo`, `saldo`, `fecha_creacion`) VALUES
  (1, 1, 'CAJA_AHORRO', '15000.50', '2025-03-25 15:37:49'),
  (2, 1, 'CUENTA_CORRIENTE', '5000.75', '2025-03-25 15:37:49'),
  (3, 2, 'CAJA_AHORRO', '30000.00', '2025-03-25 15:37:49'),
  (4, 3, 'CUENTA_CORRIENTE', '12000.20', '2025-03-25 15:37:49'),
  (5, 4, 'CAJA_AHORRO', '8000.90', '2025-03-25 15:37:49'),
  (6, 5, 'CUENTA_CORRIENTE', '25000.00', '2025-03-25 15:37:49');

INSERT INTO `credenciales` (`id_credencial`, `id_usuario`, `username`, `password`, `permiso`) VALUES
  (1, 1, 'juanperez', '1234', 'CLIENTE'),
  (2, 2, 'mariagomez', '1234', 'ADMINISTRADOR'),
  (3, 3, 'carloslopez', '1234', 'GESTOR'),
  (4, 4, 'anamartinez', '1234', 'CLIENTE'),
  (5, 5, 'pedrofernandez', 'hashedpassword5', 'ADMINISTRADOR');

ALTER TABLE `usuarios` AUTO_INCREMENT = 6;
ALTER TABLE `cuentas` AUTO_INCREMENT = 7;
ALTER TABLE `credenciales` AUTO_INCREMENT = 6;

SET FOREIGN_KEY_CHECKS = 1;

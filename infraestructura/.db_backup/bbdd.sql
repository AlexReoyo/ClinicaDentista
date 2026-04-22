-- =========================================
-- CREAR BASE DE DATOS
-- =========================================

USE dentista;

-- =========================================
-- CREACIÓN DE TABLAS
-- =========================================


CREATE TABLE if not exists pacientes (
    idPaciente INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100),
    telefono VARCHAR(20)
);

CREATE TABLE if not exists doctores (
    idDoctor INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    especialidad VARCHAR(100)
);

CREATE TABLE if not exists citas  (
    idCita INT PRIMARY KEY AUTO_INCREMENT,
    idPaciente INT,
    idDoctor INT,
    fecha DATE,
    motivo VARCHAR(255),

    FOREIGN KEY (idPaciente) REFERENCES pacientes(idPaciente),
    FOREIGN KEY (idDoctor) REFERENCES doctores(idDoctor)
);

-- =========================================
-- INSERTS DE PRUEBA
-- =========================================

INSERT INTO pacientes (nombre, apellido, telefono) VALUES
('Juan', 'Pérez', '600111222'),
('Ana', 'García', '600333444'),
('Luis', 'Martínez', '600555666');

INSERT INTO doctores (nombre, especialidad) VALUES
('Dr. López', 'Odontología general'),
('Dr. Sánchez', 'Ortodoncia'),
('Dr. Ruiz', 'Endodoncia');

INSERT INTO citas (idPaciente, idDoctor, fecha, motivo) VALUES
(1, 1, '2026-05-01', 'Revisión dental'),
(2, 2, '2026-05-02', 'Brackets'),
(3, 3, '2026-05-03', 'Dolor de muela'),
(1, 2, '2026-05-04', 'Ajuste de ortodoncia');

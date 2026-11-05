CREATE DATABASE IF NOT EXISTS tiendadb CHARACTER SET utf8mb4;
USE tiendadb;

CREATE TABLE productos (
    id_producto INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    stock INT
);

CREATE TABLE pedidos (
    id_pedido INT AUTO_INCREMENT PRIMARY KEY,
    fecha_pedido DATE NOT NULL,
    cantidad INT NOT NULL,
    id_producto INT,
    CONSTRAINT fk_pedido_producto
        FOREIGN KEY (id_producto) REFERENCES productos(id_producto)
);
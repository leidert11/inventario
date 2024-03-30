SELECT table_name
FROM information_schema.tables
WHERE table_schema = 'public'
AND table_type = 'BASE TABLE';

create database if not exists inventario;
use inventario;

-- Tablas
CREATE TABLE productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10, 2) NOT NULL,
    cantidad_stock INT NOT NULL
);

CREATE TABLE proveedores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_empresa VARCHAR(100) NOT NULL,
    contacto_nombre VARCHAR(100),
    contacto_email VARCHAR(100),
    contacto_telefono VARCHAR(20)
);

CREATE TABLE pedidos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_producto INT,
    id_proveedor INT,
    cantidad INT NOT NULL,
    fecha_pedido DATE NOT NULL,
    estado_pedido VARCHAR(50) NOT NULL CHECK (estado_pedido IN ('pendiente', 'en proceso', 'entregado', 'cancelado')),
    FOREIGN KEY (id_producto) REFERENCES productos(id),
    FOREIGN KEY (id_proveedor) REFERENCES proveedores(id)
);

-- Consultas

-- Proveedores con el recuento total de pedidos
SELECT pr.nombre_empresa, COUNT(pd.id) AS total_pedidos
FROM proveedores pr
LEFT JOIN pedidos pd ON pr.id = pd.id_proveedor
GROUP BY pr.nombre_empresa;

-- Pedidos con cantidad superior al promedio de cantidad en stock del producto correspondiente
SELECT p.*
FROM pedidos p
INNER JOIN productos prod ON p.id_producto = prod.id
WHERE p.cantidad > (SELECT AVG(cantidad_stock) FROM productos WHERE id = p.id_producto);

-- Productos pedidos en el mes pasado
SELECT pr.nombre, pe.cantidad, pe.fecha_pedido
FROM pedidos pe
JOIN productos pr ON pe.id_producto = pr.id
WHERE pe.fecha_pedido >= DATE_SUB(CURDATE(), INTERVAL 1 MONTH);

-- Proveedores con la cantidad total de productos que han sido pedidos en estado "entregado"
SELECT pr.nombre_empresa, SUM(pd.cantidad) AS total_pedidos_entregados
FROM proveedores pr
INNER JOIN pedidos pd ON pr.id = pd.id_proveedor
WHERE pd.estado_pedido = 'entregado'
GROUP BY pr.nombre_empresa;

-- Proveedores y sus productos más caros pedidos
SELECT pr.nombre_empresa, p.nombre AS nombre_producto, MAX(p.precio) AS precio_maximo
FROM proveedores pr
INNER JOIN pedidos pd ON pr.id = pd.id_proveedor
INNER JOIN productos p ON pd.id_producto = p.id
GROUP BY pr.nombre_empresa, p.nombre;

-- Productos que han sido pedidos por cada proveedor y su cantidad total pedida
SELECT pr.nombre_empresa, p.nombre, SUM(pd.cantidad) AS total_pedidos
FROM proveedores pr
INNER JOIN pedidos pd ON pr.id = pd.id_proveedor
INNER JOIN productos p ON pd.id_producto = p.id
GROUP BY pr.nombre_empresa, p.nombre;

-- Número total de pedidos realizados para cada empresa proveedora
SELECT pr.nombre_empresa, COUNT(*) AS total_pedidos
FROM proveedores pr
JOIN pedidos pd ON pr.id = pd.id_proveedor
GROUP BY pr.nombre_empresa;

-- Precio promedio de los productos que no han sido pedidos en los últimos 30 días
SELECT p.nombre, AVG(p.precio) AS precio_promedio
FROM productos p
LEFT JOIN pedidos pd ON p.id = pd.id_producto AND pd.fecha_pedido >= DATE_SUB(CURDATE(), INTERVAL 30 DAY)
WHERE pd.id IS NULL
GROUP BY p.nombre;

-- Productos que no han sido pedidos en el último trimestre
SELECT p.nombre
FROM productos p
LEFT JOIN pedidos pd ON p.id = pd.id_producto AND pd.fecha_pedido >= DATE_SUB(CURDATE(), INTERVAL 3 MONTH)
WHERE pd.id IS NULL;

-- Nombre y cantidad de productos cuyo precio es mayor que el promedio de todos los productos
SELECT nombre, cantidad_stock
FROM productos
WHERE precio > (SELECT AVG(precio) FROM productos);

-- Inserción de datos en la tabla productos
INSERT INTO productos (nombre, descripcion, precio, cantidad_stock) VALUES
('Camisa de algodón', 'Camisa de algodón de manga larga', 29.99, 50),
('Pantalones vaqueros', 'Pantalones vaqueros ajustados', 39.99, 40),
('Vestido floral', 'Vestido floral con escote en V', 49.99, 30),
('Sudadera con capucha', 'Sudadera con capucha de algodón', 34.99, 45),
('Zapatillas deportivas', 'Zapatillas deportivas para correr', 59.99, 25),
('Chaqueta de cuero', 'Chaqueta de cuero genuino', 89.99, 20),
('Blusa de seda', 'Blusa de seda con estampado de lunares', 44.99, 35),
('Shorts de mezclilla', 'Shorts de mezclilla de corte alto', 24.99, 55),
('Abrigo de invierno', 'Abrigo acolchado para el invierno', 79.99, 15),
('Falda plisada', 'Falda plisada hasta la rodilla', 34.99, 30),
('Pijama de algodón', 'Pijama de algodón suave y cómodo', 29.99, 40),
('Bufanda de lana', 'Bufanda tejida de lana', 19.99, 60),
('Jersey de punto', 'Jersey de punto grueso', 39.99, 25),
('Camiseta estampada', 'Camiseta con estampado gráfico', 14.99, 50),
('Gorro de lana', 'Gorro tejido de lana', 9.99, 70),
('Traje de baño', 'Traje de baño de una pieza', 49.99, 20),
('Pantalones cortos deportivos', 'Pantalones cortos deportivos para mujer', 24.99, 35),
('Blazer formal', 'Blazer formal para ocasiones especiales', 69.99, 10),
('Vestido de noche', 'Vestido de noche elegante', 79.99, 15),
('Polo de manga corta', 'Polo de manga corta para hombre', 24.99, 40),
('Cinturón de cuero', 'Cinturón de cuero genuino', 29.99, 50),
('Sombrero de paja', 'Sombrero de paja estilo fedora', 19.99, 30),
('Vestido de verano', 'Vestido ligero y fresco para el verano', 39.99, 25),
('Chaleco acolchado', 'Chaleco acolchado para el otoño', 49.99, 20),
('Calcetines de algodón', 'Calcetines de algodón suaves y transpirables', 9.99, 100),
('Blusa sin mangas', 'Blusa sin mangas de tela liviana', 29.99, 45),
('Chaquetón de lana', 'Chaquetón de lana para el invierno', 59.99, 20),
('Sudadera con cremallera', 'Sudadera con cremallera y capucha', 39.99, 30),
('Pantalones formales', 'Pantalones formales para hombre', 49.99, 25),
('Vestido de cóctel', 'Vestido elegante para ocasiones especiales', 59.99, 15);

-- Inserción de datos en la tabla proveedores
INSERT INTO proveedores (nombre_empresa, contacto_nombre, contacto_email, contacto_telefono) VALUES
('FabricaTextil S.A.', 'Juan Perez', 'juan@fabricatextil.com', '+123456789'),
('ModaFashion Ltda.', 'Maria Rodriguez', 'maria@modafashion.com', '+987654321'),
('RopaEstilizada E.I.R.L.', 'Carlos Sanchez', 'carlos@ropaestilizada.com', '+1122334455'),
('Textiles del Sur', 'Luisa Gomez', 'luisa@textilesdelsur.com', '+9988776655'),
('Indumentaria Moderna S.R.L.', 'Laura Fernandez', 'laura@indumentariamoderna.com', '+5544332211');

-- Inserción de datos en la tabla pedidos
INSERT INTO pedidos (id_producto, id_proveedor, cantidad, fecha_pedido, estado_pedido) VALUES
(1, 1, 20, '2023-01-10', 'entregado'),
(2, 2, 15, '2023-02-15', 'en proceso'),
(3, 3, 10, '2023-03-20', 'pendiente'),
(4, 4, 25, '2023-04-25', 'entregado'),
(5, 5, 30, '2023-05-30', 'en proceso'),
(6, 1, 20, '2023-06-05', 'entregado'),
(7, 2, 15, '2023-07-10', 'en proceso'),
(8, 3, 10, '2023-08-15', 'pendiente'),
(9, 4, 25, '2023-09-20', 'entregado'),
(10, 5, 30, '2023-10-25', 'en proceso'),
(11, 1, 20, '2023-11-30', 'entregado'),
(12, 2, 15, '2023-12-05', 'en proceso'),
(13, 3, 10, '2024-01-10', 'pendiente'),
(14, 4, 25, '2024-02-15', 'entregado'),
(15, 5, 30, '2024-03-20', 'en proceso'),
(16, 1, 20, '2024-04-25', 'entregado'),
(17, 2, 15, '2024-05-30', 'en proceso'),
(18, 3, 10, '2024-06-05', 'pendiente'),
(19, 4, 25, '2024-07-10', 'entregado'),
(20, 5, 30, '2024-08-15', 'en proceso'),
(21, 1, 20, '2024-09-20', 'entregado'),
(22, 2, 15, '2024-10-25', 'pendiente'),
(23, 3, 10, '2024-11-30', 'cancelado'),
(24, 4, 25, '2024-12-05', 'entregado'),
(25, 5, 30, '2025-01-10', 'cancelado'),
(26, 1, 20, '2025-02-15', 'pendiente'),
(27, 2, 15, '2025-03-20', 'en proceso'),
(28, 3, 10, '2025-04-25', 'pendiente'),
(29, 4, 25, '2025-05-30', 'entregado'),
(30, 5, 30, '2025-06-05', 'cancelado');

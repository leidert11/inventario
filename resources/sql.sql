SELECT table_name
FROM information_schema.tables
WHERE table_schema = 'public'
AND table_type = 'BASE TABLE';

CREATE TABLE productos (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10, 2) NOT NULL,
    cantidad_stock INT NOT NULL
);

CREATE TABLE proveedores (
    id SERIAL PRIMARY KEY,
    nombre_empresa VARCHAR(100) NOT NULL,
    contacto_nombre VARCHAR(100),
    contacto_email VARCHAR(100),
    contacto_telefono VARCHAR(20)
);

CREATE TABLE pedidos (
    id SERIAL PRIMARY KEY,
    id_producto INT,
    id_proveedor INT,
    cantidad INT NOT NULL,
    fecha_pedido DATE NOT NULL,
    estado_pedido VARCHAR(50) NOT NULL CHECK (estado_pedido IN ('pendiente', 'en proceso', 'entregado', 'cancelado')),
    FOREIGN KEY (id_producto) REFERENCES productos(id),
    FOREIGN KEY (id_proveedor) REFERENCES proveedores(id)
);
-- pedido


-- consulta 1 Proveedores con el recuento total de pedidos:
SELECT pr.nombre_empresa, COUNT(pd.id) AS total_pedidos
FROM proveedores pr
LEFT JOIN pedidos pd ON pr.id = pd.id_proveedor
GROUP BY pr.nombre_empresa;


--consulta 2 Pedidos con cantidad superior al promedio de cantidad en stock del producto correspondiente:
SELECT p.*
FROM pedidos p
INNER JOIN productos prod ON p.id_producto = prod.id
WHERE p.cantidad > (SELECT AVG(cantidad_stock) FROM productos WHERE id = p.id_producto);

-- Consulta 3 - Productos pedidos en el mes pasado:
SELECT pr.nombre, pe.cantidad, pe.fecha_pedido
FROM pedidos pe
JOIN productos pr ON pe.id_producto = pr.id
WHERE pe.fecha_pedido >= (CURRENT_DATE - INTERVAL '1 month');

--proveedor

-- Consulta 1 - Proveedores con la cantidad total de productos que han sido pedidos en estado "entregado":
SELECT pr.nombre_empresa, SUM(pd.cantidad) AS total_pedidos_entregados
FROM proveedores pr
INNER JOIN pedidos pd ON pr.id = pd.id_proveedor
WHERE pd.estado_pedido = 'entregado'
GROUP BY pr.nombre_empresa;

-- Consulta 2 - Proveedores y sus productos más caros pedidos:
SELECT pr.nombre_empresa, p.nombre AS nombre_producto, MAX(precio) AS precio_maximo
FROM proveedores pr
INNER JOIN pedidos pd ON pr.id = pd.id_proveedor
INNER JOIN productos p ON pd.id_producto = p.id
GROUP BY pr.nombre_empresa, p.nombre;
--Consulta 3 - Productos que han sido pedidos por cada proveedor y su cantidad total pedida:
SELECT pr.nombre_empresa, p.nombre, SUM(pd.cantidad) AS total_pedidos
FROM proveedores pr
INNER JOIN pedidos pd ON pr.id = pd.id_proveedor
INNER JOIN productos p ON pd.id_producto = p.id
GROUP BY pr.nombre_empresa, p.nombre;

--  consulta 4Obtener el número total de pedidos realizados para cada empresa proveedora
SELECT pr.nombre_empresa, COUNT(*) AS total_pedidos
FROM proveedores pr
JOIN pedidos pd ON pr.id = pd.id_proveedor
GROUP BY pr.nombre_empresa;


-- producto
--Calcular 1 el precio promedio de los productos que no han sido pedidos en los últimos 30 días
SELECT p.nombre, AVG(precio) AS precio_promedio
FROM productos p
LEFT JOIN pedidos pd ON p.id = pd.id_producto AN D pd.fecha_pedido >= (CURRENT_DATE - INTERVAL '30 days')
WHERE pd.id IS NULL
GROUP BY p.nombre;

-- Consulta 2 - Productos que no han sido pedidos en el último trimestre:
SELECT p.nombre
FROM productos p
LEFT JOIN pedidos pd ON p.id = pd.id_producto AND pd.fecha_pedido >= (CURRENT_DATE - INTERVAL '3 months')
WHERE pd.id IS NULL;


-- Consulta 3: Obtener el nombre y la cantidad de productos cuyo precio es mayor que el promedio de todos los productos.
SELECT nombre, cantidad_stock
FROM productos
WHERE precio > (SELECT AVG(precio) FROM productos);




INSERT INTO productos (id, nombre, descripcion, precio, cantidad_stock) VALUES
(1, 'Camisa de algodón', 'Camisa de algodón de manga larga', 29.99, 50),
(2, 'Pantalones vaqueros', 'Pantalones vaqueros ajustados', 39.99, 40),
(3, 'Vestido floral', 'Vestido floral con escote en V', 49.99, 30),
(4, 'Sudadera con capucha', 'Sudadera con capucha de algodón', 34.99, 45),
(5, 'Zapatillas deportivas', 'Zapatillas deportivas para correr', 59.99, 25),
(6, 'Chaqueta de cuero', 'Chaqueta de cuero genuino', 89.99, 20),
(7, 'Blusa de seda', 'Blusa de seda con estampado de lunares', 44.99, 35),
(8, 'Shorts de mezclilla', 'Shorts de mezclilla de corte alto', 24.99, 55),
(9, 'Abrigo de invierno', 'Abrigo acolchado para el invierno', 79.99, 15),
(10, 'Falda plisada', 'Falda plisada hasta la rodilla', 34.99, 30),
(11, 'Pijama de algodón', 'Pijama de algodón suave y cómodo', 29.99, 40),
(12, 'Bufanda de lana', 'Bufanda tejida de lana', 19.99, 60),
(13, 'Jersey de punto', 'Jersey de punto grueso', 39.99, 25),
(14, 'Camiseta estampada', 'Camiseta con estampado gráfico', 14.99, 50),
(15, 'Gorro de lana', 'Gorro tejido de lana', 9.99, 70),
(16, 'Traje de baño', 'Traje de baño de una pieza', 49.99, 20),
(17, 'Pantalones cortos deportivos', 'Pantalones cortos deportivos para mujer', 24.99, 35),
(18, 'Blazer formal', 'Blazer formal para ocasiones especiales', 69.99, 10),
(19, 'Vestido de noche', 'Vestido de noche elegante', 79.99, 15),
(20, 'Polo de manga corta', 'Polo de manga corta para hombre', 24.99, 40),
(21, 'Cinturón de cuero', 'Cinturón de cuero genuino', 29.99, 50),
(22, 'Sombrero de paja', 'Sombrero de paja estilo fedora', 19.99, 30),
(23, 'Vestido de verano', 'Vestido ligero y fresco para el verano', 39.99, 25),
(24, 'Chaleco acolchado', 'Chaleco acolchado para el otoño', 49.99, 20),
(25, 'Calcetines de algodón', 'Calcetines de algodón suaves y transpirables', 9.99, 100),
(26, 'Blusa sin mangas', 'Blusa sin mangas de tela liviana', 29.99, 45),
(27, 'Chaquetón de lana', 'Chaquetón de lana para el invierno', 59.99, 20),
(28, 'Sudadera con cremallera', 'Sudadera con cremallera y capucha', 39.99, 30),
(29, 'Pantalones formales', 'Pantalones formales para hombre', 49.99, 25),
(30, 'Vestido de cóctel', 'Vestido elegante para ocasiones especiales', 59.99, 15);



INSERT INTO proveedores (id, nombre_empresa, contacto_nombre, contacto_email, contacto_telefono) VALUES
(1, 'FabricaTextil S.A.', 'Juan Perez', 'juan@fabricatextil.com', '+123456789'),
(2, 'ModaFashion Ltda.', 'Maria Rodriguez', 'maria@modafashion.com', '+987654321'),
(3, 'RopaEstilizada E.I.R.L.', 'Carlos Sanchez', 'carlos@ropaestilizada.com', '+1122334455'),
(4, 'Textiles del Sur', 'Luisa Gomez', 'luisa@textilesdelsur.com', '+9988776655'),
(5, 'Indumentaria Moderna S.R.L.', 'Laura Fernandez', 'laura@indumentariamoderna.com', '+5544332211');


INSERT INTO pedidos (id, id_producto, id_proveedor, cantidad, fecha_pedido, estado_pedido) VALUES
(1, 1, 1, 20, '2023-01-10', 'entregado'),
(2, 2, 2, 15, '2023-02-15', 'en proceso'),
(3, 3, 3, 10, '2023-03-20', 'pendiente'),
(4, 4, 4, 25, '2023-04-25', 'entregado'),
(5, 5, 5, 30, '2023-05-30', 'en proceso'),
(6, 6, 1, 20, '2023-06-05', 'entregado'),
(7, 7, 2, 15, '2023-07-10', 'en proceso'),
(8, 8, 3, 10, '2023-08-15', 'pendiente'),
(9, 9, 4, 25, '2023-09-20', 'entregado'),
(10, 10, 5, 30, '2023-10-25', 'en proceso'),
(11, 11, 1, 20, '2023-11-30', 'entregado'),
(12, 12, 2, 15, '2023-12-05', 'en proceso'),
(13, 13, 3, 10, '2024-01-10', 'pendiente'),
(14, 14, 4, 25, '2024-02-15', 'entregado'),
(15, 15, 5, 30, '2024-03-20', 'en proceso'),
(16, 16, 1, 20, '2024-04-25', 'entregado'),
(17, 17, 2, 15, '2024-05-30', 'en proceso'),
(18, 18, 3, 10, '2024-06-05', 'pendiente'),
(19, 19, 4, 25, '2024-07-10', 'entregado'),
(20, 20, 5, 30, '2024-08-15', 'en proceso'),
(21, 21, 1, 20, '2024-09-20', 'entregado'),
(22, 22, 2, 15, '2024-10-25', 'pendiente'),
(23, 23, 3, 10, '2024-11-30', 'cancelado'),
(24, 24, 4, 25, '2024-12-05', 'entregado'),
(25, 25, 5, 30, '2025-01-10', 'cancelado'),
(26, 26, 1, 20, '2025-02-15', 'pendiente'),
(27, 27, 2, 15, '2025-03-20', 'en proceso'),
(28, 28, 3, 10, '2025-04-25', 'pendiente'),
(29, 29, 4, 25, '2025-05-30', 'entregado'),
(30, 30, 5, 30, '2025-06-05', 'cancelado');

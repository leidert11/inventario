package com.manejadorInventario.inventario.domain.repository;

import com.manejadorInventario.inventario.persistence.Producto;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    // Consulta 1: Calcular el precio promedio de los productos que no han sido
    // pedidos en los últimos 30 días
    @Query("SELECT p.nombre, AVG(p.precio) AS precioPromedio " +
            "FROM Producto p " +
            "LEFT JOIN Pedido pd ON p.id = pd.producto.id AND pd.fechaPedido >= :fechaInicio " +
            "WHERE pd.id IS NULL " +
            "GROUP BY p.nombre")
    List<Object[]> findTotalPedidosEntregadosByProveedor(@Param("fechaInicio") Date fechaInicio);

    // Consulta 2: Productos que no han sido pedidos en el último trimestre
    @Query("SELECT p.nombre " +
            "FROM Producto p " +
            "LEFT JOIN Pedido pd ON p.id = pd.producto.id AND pd.fechaPedido >= :fechaInicio " +
            "WHERE pd.id IS NULL")
    List<String> findProductosMasCarosPedidosByProveedor(@Param("fechaInicio") Date  fechaInicio);

    // Consulta 3: Obtener el nombre y la cantidad de productos cuyo precio es mayor
    // que el promedio de todos los productos
    @Query("SELECT p.nombre, p.cantidadStock " +
            "FROM Producto p " +
            "WHERE p.precio > (SELECT AVG(precio) FROM Producto)")
    List<Object[]> findProductosPrecioSuperiorPromedio();

}

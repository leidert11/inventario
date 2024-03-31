package com.manejadorInventario.inventario.domain.repository;

import com.manejadorInventario.inventario.persistence.Producto;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    @Query("SELECT p.nombre, AVG(p.precio) AS precioPromedio " +
            "FROM Producto p " +
            "LEFT JOIN Pedido pd ON p.id = pd.producto.id " +
            "WHERE pd.id IS NULL AND pd.fechaPedido >= current_timestamp() - 30 * 24 * 60 * 60 * 1000 " +
            "GROUP BY p.nombre")
    List<Object[]> findTotalPedidosEntregadosByProveedor();

    @Query("SELECT p.nombre " +
            "FROM Producto p " +
            "LEFT JOIN Pedido pd ON p.id = pd.producto.id AND pd.fechaPedido >= current_timestamp() - 3 * 30 * 24 * 60 * 60 * 1000 " +
            "WHERE pd.id IS NULL " +
            "GROUP BY p.nombre")
    List<String> findProductosMasCarosPedidosByProveedor();

    // Consulta 3: Obtener el nombre y la cantidad de productos cuyo precio es mayor
    // que el promedio de todos los productos@Query("SELECT p.nombre " +
    @Query("SELECT p.nombre " +
       "FROM Producto p " +
       "LEFT JOIN Pedido pd ON p.id = pd.producto.id AND pd.fechaPedido >= current_timestamp() - 3 * 30 * 24 * 60 * 60 * 1000 " +
       "WHERE pd.id IS NULL")
    List<Object[]> findProductosPrecioSuperiorPromedio();
}
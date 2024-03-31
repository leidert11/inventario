package com.manejadorInventario.inventario.domain.repository;

import com.manejadorInventario.inventario.persistence.Pedido;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    // Consulta 1: Proveedores con la cantidad total de productos que han sido
    // pedidos en estado "entregado"
    @Query("SELECT pr.nombreEmpresa, SUM(pe.cantidad) AS totalPedidosEntregados " +
            "FROM Proveedor pr " +
            "INNER JOIN pr.pedidos pe " +
            "WHERE pe.estadoPedido = 'entregado' " +
            "GROUP BY pr.nombreEmpresa")
    List<Object[]> findTotalPedidosEntregadosByProveedor();

    // Consulta 2: Proveedores y sus productos más caros pedidos
    @Query("SELECT pr.nombreEmpresa, pe.producto.nombre AS nombreProducto, MAX(pe.producto.precio) AS precioMaximo " +
            "FROM Proveedor pr " +
            "INNER JOIN pr.pedidos pe " +
            "GROUP BY pr.nombreEmpresa, pe.producto.nombre")
    List<Object[]> findProductosMasCarosPedidosByProveedor();

}

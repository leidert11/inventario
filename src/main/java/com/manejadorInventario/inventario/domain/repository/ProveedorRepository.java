package com.manejadorInventario.inventario.domain.repository;

import com.manejadorInventario.inventario.persistence.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {
    // Consulta 1: Proveedores con la cantidad total de productos que han sido
    // pedidos en estado "entregado"
    @Query("SELECT pr.nombreEmpresa, SUM(pd.cantidad) AS totalPedidosEntregados " +
            "FROM Proveedor pr " +
            "INNER JOIN Pedido pd ON pr.id = pd.proveedor.id " +
            "WHERE pd.estadoPedido = 'entregado' " +
            "GROUP BY pr.nombreEmpresa")
    List<Object[]> findTotalPedidosEntregadosByProveedor();

    // Consulta 2: Proveedores y sus productos más caros pedidos
    @Query("SELECT pr.nombreEmpresa, p.nombre AS nombreProducto, MAX(p.precio) AS precioMaximo " +
            "FROM Proveedor pr " +
            "INNER JOIN Pedido pd ON pr.id = pd.proveedor.id " +
            "INNER JOIN Producto p ON pd.producto.id = p.id " +
            "GROUP BY pr.nombreEmpresa, p.nombre")
    List<Object[]> findProductosMasCarosPedidosByProveedor();

    // Consulta 3: Productos que han sido pedidos por cada proveedor y su cantidad
    // total pedida
    @Query("SELECT pr.nombreEmpresa, p.nombre, SUM(pd.cantidad) AS totalPedidos " +
            "FROM Proveedor pr " +
            "INNER JOIN Pedido pd ON pr.id = pd.proveedor.id " +
            "INNER JOIN Producto p ON pd.producto.id = p.id " +
            "GROUP BY pr.nombreEmpresa, p.nombre")
    List<Object[]> findProductosPedidosByProveedor();

}

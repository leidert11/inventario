package com.manejadorInventario.inventario.persistence;

import jakarta.persistence.*;

import java.util.Date;


@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_proveedor",referencedColumnName = "id")
    private Proveedor proveedor;

    @ManyToOne
    @JoinColumn(name = "id_producto",referencedColumnName = "id")
    private Producto producto;

    private Integer cantidad;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_pedido")
    private Date fechaPedido;

    @Column(name = "estado_pedido")
    private String estadoPedido;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Date fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public String getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(String estadoPedido) {
        this.estadoPedido = estadoPedido;
    }
}

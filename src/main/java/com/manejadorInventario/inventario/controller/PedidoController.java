package com.manejadorInventario.inventario.controller;

import com.manejadorInventario.inventario.domain.exception.EntityNotFoundException;
import com.manejadorInventario.inventario.domain.service.PedidoService;
import com.manejadorInventario.inventario.persistence.Pedido;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    // CRUD
    @GetMapping("/all")
    public List<Pedido> findAllPedidos() {
        return pedidoService.findAll();
    }

    @GetMapping("/{id}")
    public Pedido findPedidoById(@PathVariable Integer id) {
        return pedidoService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado con id: " + id));
    }

    @PostMapping("/create")
    public Pedido createPedido(@Valid @RequestBody Pedido pedido) {
        return pedidoService.save(pedido);
    }

    @PutMapping("/{id}")
    public Pedido updatePedido(@PathVariable Integer id, @Valid @RequestBody Pedido pedidoDetails) {
        Pedido existingPedido = pedidoService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado con id: " + id));

        existingPedido.setCantidad(pedidoDetails.getCantidad());
        existingPedido.setFechaPedido(pedidoDetails.getFechaPedido());
        existingPedido.setEstadoPedido(pedidoDetails.getEstadoPedido());

        return pedidoService.save(existingPedido);
    }

    @DeleteMapping("/{id}")
    public void deletePedido(@PathVariable Integer id) {
        pedidoService.deleteById(id);
    }

    // Consulta 1: Proveedores con la cantidad total de productos que han sido
// pedidos en estado "entregado"
    @GetMapping("/totalPedidosEntregadosByProveedor")
    public List<Object[]> findTotalPedidosEntregadosByProveedor() {
        return pedidoService.findTotalPedidosEntregadosByProveedor();
    }

    // Consulta 2: Proveedores y sus productos más caros pedidos
    @GetMapping("/productosMasCarosPedidosByProveedor")
    public List<Object[]> findProductosMasCarosPedidosByProveedor() {
        return pedidoService.findProductosMasCarosPedidosByProveedor();
    }



}
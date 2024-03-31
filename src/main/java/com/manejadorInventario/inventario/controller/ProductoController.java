package com.manejadorInventario.inventario.controller;

import com.manejadorInventario.inventario.domain.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/producto")
public class ProductoController {
    @Autowired
    private ProductoService productoService;
    @GetMapping("/precioPromedioUltimos30Dias")
    public List<Object[]> precioPromedioUltimos30Dias() {
        return productoService.precioPromedioProductosNoPedidosUltimos30Dias();
    }

    @GetMapping("/productosNoPedidosUltimoTrimestre")
    public List<String> productosNoPedidosUltimoTrimestre() {
        return productoService.productosNoPedidosUltimoTrimestre();
    }

    @GetMapping("/productosPrecioSuperiorPromedio")
    public List<Object[]> productosPrecioSuperiorPromedio() {
        return productoService.productosPrecioSuperiorPromedio();
    }
}

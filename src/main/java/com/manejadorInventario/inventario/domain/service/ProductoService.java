package com.manejadorInventario.inventario.domain.service;


import com.manejadorInventario.inventario.domain.exception.EntityNotFoundException;
import com.manejadorInventario.inventario.domain.exception.InvalidArgumentException;
import com.manejadorInventario.inventario.domain.exception.NullReferenceException;
import com.manejadorInventario.inventario.domain.repository.ProductoRepository;
import com.manejadorInventario.inventario.persistence.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;


@Service
public class ProductoService implements ServiceInterface<Producto> {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    @Override
    public Optional<Producto> findById(Integer id) {
        Optional<Producto> producto = productoRepository.findById(id);
        if (!producto.isPresent()) {
            throw new EntityNotFoundException("Producto no encontrado con id: " + id);
        }
        return producto;
    }

    @Override
    public Producto save(Producto producto) {
        if (producto == null) {
            throw new NullReferenceException("El producto no puede ser nulo");
        }
        if (producto.getNombre() == null || producto.getDescripcion() == null || producto.getPrecio() == null || producto.getCantidadStock() == null) {
            throw new InvalidArgumentException("Los datos del producto no pueden estar vacios");
        }
        return productoRepository.save(producto);
    }

    @Override
    public void deleteById(Integer id) {
        if (!productoRepository.existsById(id)) {
            throw new EntityNotFoundException("Producto no encontrado con id: " + id);
        }
        productoRepository.deleteById(id);
    }

    @Override
    public Producto update(Producto productoDetails) {
        if (productoDetails == null) {
            throw new NullReferenceException("Los detalles del producto no pueden ser nulos");
        }
        Integer productId = productoDetails.getId();
        Producto existingProducto = productoRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con id: " + productId));

        Field[] fields = Producto.class.getDeclaredFields();

        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(productoDetails);
                if (value != null) {
                    field.set(existingProducto, value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return productoRepository.save(existingProducto);
    }
}
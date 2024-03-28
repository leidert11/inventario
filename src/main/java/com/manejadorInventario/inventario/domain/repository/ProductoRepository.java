package com.manejadorInventario.inventario.domain.repository;

import com.manejadorInventario.inventario.persistence.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    
}

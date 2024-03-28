package com.manejadorInventario.inventario.domain.repository;

import com.manejadorInventario.inventario.persistence.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {
    
}

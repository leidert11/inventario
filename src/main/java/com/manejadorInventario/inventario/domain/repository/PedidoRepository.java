package com.manejadorInventario.inventario.domain.repository;

import com.manejadorInventario.inventario.persistence.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    
}

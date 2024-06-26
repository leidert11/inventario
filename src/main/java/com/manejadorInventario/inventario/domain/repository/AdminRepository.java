package com.manejadorInventario.inventario.domain.repository;

import com.manejadorInventario.inventario.persistence.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByUsername(String username);
}

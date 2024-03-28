package com.manejadorInventario.inventario.domain.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ServiceInterface<T> {
    List<T> findAll();
    Optional<T> findById(Integer id);
    T save(T object);
    void deleteById(Integer id);
    T update(T object);
}
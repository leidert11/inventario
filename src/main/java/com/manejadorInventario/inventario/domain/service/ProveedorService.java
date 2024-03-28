package com.manejadorInventario.inventario.domain.service;

import com.manejadorInventario.inventario.domain.exception.EntityNotFoundException;
import com.manejadorInventario.inventario.domain.exception.InvalidArgumentException;
import com.manejadorInventario.inventario.domain.exception.NullReferenceException;
import com.manejadorInventario.inventario.domain.repository.ProveedorRepository;
import com.manejadorInventario.inventario.persistence.Proveedor;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

public class ProveedorService implements ServiceInterface<Proveedor> {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Override
    public List<Proveedor> findAll() {
        return proveedorRepository.findAll();
    }

    @Override
    public Optional<Proveedor> findById(Integer id) {
        Optional<Proveedor> proveedor = proveedorRepository.findById(id);
        if (!proveedor.isPresent()) {
            throw new EntityNotFoundException("Proveedor no encontrado con id: " + id);
        }
        return proveedor;
    }

    @Override
    public Proveedor save(Proveedor proveedor) {
        if (proveedor == null) {
             throw new NullReferenceException("El proveedor no puede ser nulo");
        }
        if (proveedor.getNombreEmpresa() == null ||proveedor.getContactoNombre() == null || proveedor.getContactoEmail() == null || proveedor.getContactoTelefono() == null) {
            throw new InvalidArgumentException("Los datos del proveedor no pueden estar vacios");
        }
        return proveedorRepository.save(proveedor);
    }

    @Override
    public void deleteById(Integer id) {
        if (!proveedorRepository.existsById(id)) {
            throw new EntityNotFoundException("Proveedor no encontrado con id: " + id);
        }
        proveedorRepository.deleteById(id);
    }

    @Override
    public Proveedor update(Proveedor proveedorDetails) {
        if ( proveedorDetails == null) {
            throw new NullReferenceException("Los detalles del proveedor no pueden ser nulos");
    }
        Integer proveedorId = proveedorDetails.getId();
        Proveedor existingProveedor = proveedorRepository.findById(proveedorId)
                .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado con id: " + proveedorId));
        Field[] fields = Proveedor.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                field.set(proveedorDetails, field.get(existingProveedor));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return proveedorRepository.save(proveedorDetails);
    }
}

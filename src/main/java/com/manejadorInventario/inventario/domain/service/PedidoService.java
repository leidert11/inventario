package com.manejadorInventario.inventario.domain.service;

import com.manejadorInventario.inventario.domain.exception.EntityNotFoundException;
import com.manejadorInventario.inventario.domain.exception.InvalidArgumentException;
import com.manejadorInventario.inventario.domain.exception.NullReferenceException;
import com.manejadorInventario.inventario.domain.repository.PedidoRepository;
import com.manejadorInventario.inventario.persistence.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService implements ServiceInterface<Pedido> {

    @Autowired
    private PedidoRepository pedidoRepository;

    // CRUD
    @Override
    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public Optional<Pedido> findById(Integer id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        if (!pedido.isPresent()) {
            throw new EntityNotFoundException("Pedido no encontrado con id: " + id);
        }
        return pedido;
    }

    @Override
    public Pedido save(Pedido pedido) {
        if (pedido == null) {
            throw new NullReferenceException("El pedido no puede ser nulo");
        }
        if (pedido.getFechaPedido() == null || pedido.getCantidad() == null || pedido.getEstadoPedido() == null) {
            throw new InvalidArgumentException("Los datos del pedido no pueden estar vacios");
        }
        return pedidoRepository.save(pedido);
    }

    @Override
    public void deleteById(Integer id) {
        if (!pedidoRepository.existsById(id)) {
            throw new EntityNotFoundException("Pedido no encontrado con id: " + id);
        }
        pedidoRepository.deleteById(id);
    }

    @Override
    public Pedido update(Pedido pedidoDetails) {
        Integer pedidoId = pedidoDetails.getId();
        Pedido existingPedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado con id: " + pedidoId));

        // Obtener todos los campos de la clase Pedido
        Field[] fields = Pedido.class.getDeclaredFields();

        // Iterar sobre los campos y actualizar el pedido existente
        for (Field field : fields) {
            try {
                // Hacer accesible el campo (incluso si es privado)
                field.setAccessible(true);
                // Obtener el valor del campo en el objeto pedidoDetails
                Object value = field.get(pedidoDetails);
                // Si el valor no es nulo, establecerlo en el pedido existente
                if (value != null) {
                    field.set(existingPedido, value);
                }
            } catch (IllegalAccessException e) {
                // Manejar cualquier excepción de acceso ilegal aquí
                e.printStackTrace();
            }
        }

        // Guardar el pedido actualizado en la base de datos
        return pedidoRepository.save(existingPedido);
    }

    // Consulta 1: Proveedores con la cantidad total de productos que han sido
// pedidos en estado "entregado"
    public List<Object[]> findTotalPedidosEntregadosByProveedor() {
        return pedidoRepository.findTotalPedidosEntregadosByProveedor();
    }

    // Consulta 2: Proveedores y sus productos más caros pedidos
    public List<Object[]> findProductosMasCarosPedidosByProveedor() {
        return pedidoRepository.findProductosMasCarosPedidosByProveedor();
    }


}

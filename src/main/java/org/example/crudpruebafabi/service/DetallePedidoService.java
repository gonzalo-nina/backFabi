package org.example.crudpruebafabi.service;

import org.example.crudpruebafabi.model.DetallePedido;
import org.example.crudpruebafabi.model.Pedido;
import org.example.crudpruebafabi.model.Producto;
import org.example.crudpruebafabi.repository.DetallePedidoRepository;
import org.example.crudpruebafabi.repository.PedidoRepository;
import org.example.crudpruebafabi.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetallePedidoService {

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    public DetallePedido guardarDetallePedido(DetallePedido detallePedido) {
        if (detallePedido.getPedido() == null) {
            throw new IllegalArgumentException("El pedido no puede ser nulo.");
        }

        return detallePedidoRepository.save(detallePedido);
    }

    public List<DetallePedido> listarDetallesPedidos() {
        return detallePedidoRepository.findAll();
    }

    public Optional<DetallePedido> obtenerDetallePedidoPorId(Long idDetallePedido) {
        return detallePedidoRepository.findById(idDetallePedido);
    }

    public void eliminarDetallePedido(Long idDetallePedido) {
        detallePedidoRepository.deleteById(idDetallePedido);
    }
}


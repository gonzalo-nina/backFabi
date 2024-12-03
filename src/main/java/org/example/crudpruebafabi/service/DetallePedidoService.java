package org.example.crudpruebafabi.service;

import org.example.crudpruebafabi.model.DetallePedido;
import org.example.crudpruebafabi.model.Producto;
import org.example.crudpruebafabi.repository.DetallePedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DetallePedidoService {

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;
    @Autowired
    private ProductoService productoService;

    @Transactional
    public DetallePedido guardarDetallePedido(DetallePedido detallePedido) {
        if (detallePedido.getPedido() == null) {
            throw new IllegalArgumentException("El pedido no puede ser nulo.");
        }
        //Buscamos el producto que esta en el detalle del pedido
        Producto producto = productoService.obtenerProductoPorId(detallePedido.getProducto().getIdProducto())
                .orElseThrow(() -> new IllegalArgumentException("El producto no existe."));
        //Verificamos que el producto tenga stock suficiente
        if (producto.getDisponibilidad() < detallePedido.getCantidad()) {
            throw new IllegalArgumentException("No hay stock suficiente para el producto.");
        }
        producto.setDisponibilidad(producto.getDisponibilidad() - detallePedido.getCantidad());
        productoService.actualizarProducto(producto);
        return detallePedidoRepository.save(detallePedido);
    }

    public List<DetallePedido> listarDetallesPedidos() {
        return detallePedidoRepository.findAll();
    }

    public Optional<DetallePedido> obtenerDetallePedidoPorId(Long idDetallePedido) {
        return detallePedidoRepository.findById(idDetallePedido);
    }

    public List<DetallePedido> obtenerDetallesPedidoPorPedido(Long idPedido) {
        return detallePedidoRepository.findByPedido_IdPedido(idPedido);
    }

    @Transactional
    public void eliminarDetallePedido(Long idDetallePedido) {
        detallePedidoRepository.deleteById(idDetallePedido);
    }

    public List<DetallePedido> obtenerDetallesPedidoPorProducto(Long idProducto) {
        return detallePedidoRepository.findByProductoIdProducto(idProducto);
    }
}


package org.example.crudpruebafabi.service;

import jakarta.transaction.Transactional;
import org.example.crudpruebafabi.dto.PedidoDTO;
import org.example.crudpruebafabi.dto.ProductoPedidoDTO;
import org.example.crudpruebafabi.model.Cliente;
import org.example.crudpruebafabi.model.DetallePedido;
import org.example.crudpruebafabi.model.Pedido;
import org.example.crudpruebafabi.model.Producto;
import org.example.crudpruebafabi.repository.ClienteRepository;
import org.example.crudpruebafabi.repository.DetallePedidoRepository;
import org.example.crudpruebafabi.repository.PedidoRepository;
import org.example.crudpruebafabi.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    public List<PedidoDTO> listarPedidos() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        return pedidos.stream().map(this::convertirAPedidoDTO).collect(Collectors.toList());
    }

    public Optional<PedidoDTO> obtenerPedidoPorId(Long idPedido) {
        return pedidoRepository.findById(idPedido).map(this::convertirAPedidoDTO);
    }


    public void eliminarPedido(Long idPedido) {
        pedidoRepository.deleteById(idPedido);
    }

    public Pedido actualizarPedido(Pedido pedido, int nuevaCantidad) {
        Pedido pedidoActualizado = pedidoRepository.save(pedido);

        // Actualizar DetallePedido
        List<DetallePedido> detalles = detallePedidoRepository.findByPedido(pedidoActualizado);
        if (!detalles.isEmpty()) {
            DetallePedido detalle = detalles.get(0);
            detalle.setCantidad(nuevaCantidad);
            detalle.setSubtotal(nuevaCantidad * detalle.getPrecioUnitario());
            detallePedidoRepository.save(detalle);
        }

        return pedidoActualizado;
    }

    public Pedido actualizarEstadoPedido(Long idPedido, boolean nuevoEstado) {
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(idPedido);
        if (pedidoOptional.isPresent()) {
            Pedido pedido = pedidoOptional.get();
            pedido.setEstadoPedido(nuevoEstado);
            return pedidoRepository.save(pedido);
        } else {
            throw new RuntimeException("Pedido no encontrado");
        }
    }

    // Método para convertir Pedido a PedidoDTO
    private PedidoDTO convertirAPedidoDTO(Pedido pedido) {
        List<DetallePedido> detalles = detallePedidoRepository.findByPedido(pedido);

        List<ProductoPedidoDTO> productos = detalles.stream()
                .map(detalle -> new ProductoPedidoDTO(
                        detalle.getIdProducto(),
                        detalle.getCantidad(),
                        detalle.getPrecioUnitario()
                ))
                .collect(Collectors.toList());

        double subtotal = detalles.stream()
                .mapToDouble(DetallePedido::getSubtotal)
                .sum();

        return new PedidoDTO(
                pedido.getIdPedido(),
                pedido.getCliente().getIdCliente(),
                pedido.getCliente().getNombre(),
                pedido.getFechaPedido(),
                pedido.isEstadoPedido(),
                subtotal,
                productos
        );
    }

    // Método para guardar un nuevo pedido con detalles
    public Pedido guardarPedidoConDetalle(PedidoDTO pedidoDTO) {
        // Verificar y obtener cliente
        Cliente cliente = clienteRepository.findById(pedidoDTO.getIdCliente())
                .orElseThrow(() -> new IllegalArgumentException("Cliente con ID " + pedidoDTO.getIdCliente() + " no existe."));

        // Crear y guardar el pedido
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setFechaPedido(new Date());
        pedido.setEstadoPedido(false);

        Pedido nuevoPedido = pedidoRepository.save(pedido);

        double subtotalTotal = 0.0;

        // Procesar cada producto en el pedido
        for (ProductoPedidoDTO productoPedidoDTO : pedidoDTO.getProductos()) {
            Producto producto = productoRepository.findById(productoPedidoDTO.getIdProducto())
                    .orElseThrow(() -> new IllegalArgumentException("Producto con ID " + productoPedidoDTO.getIdProducto() + " no existe."));

            if (producto.getDisponibilidad() < productoPedidoDTO.getCantidad()) {
                throw new IllegalArgumentException("No hay suficiente stock del producto: " + producto.getNombre());
            }

            // Actualizar disponibilidad del producto
            producto.setDisponibilidad(producto.getDisponibilidad() - productoPedidoDTO.getCantidad());
            productoRepository.save(producto);

            // Crear y guardar detalle del pedido
            DetallePedido detallePedido = new DetallePedido();
            detallePedido.setPedido(nuevoPedido);
            detallePedido.setIdProducto(producto.getIdProducto());
            detallePedido.setCantidad(productoPedidoDTO.getCantidad());
            detallePedido.setPrecioUnitario(productoPedidoDTO.getPrecioUnitario());
            detallePedido.setSubtotal(productoPedidoDTO.getCantidad() * productoPedidoDTO.getPrecioUnitario());

            detallePedidoRepository.save(detallePedido);

            // Sumar al subtotal total
            subtotalTotal += detallePedido.getSubtotal();
        }

        // Establecer el subtotal total en el pedido
        pedido.setSubtotal(subtotalTotal);
        pedidoRepository.save(pedido);

        return nuevoPedido;
    }

    @Transactional
    public Pedido actualizarPedidoConDetalle(PedidoDTO pedidoDTO) {
        Pedido pedido = pedidoRepository.findById(pedidoDTO.getIdPedido())
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        // Actualizar cliente
        Cliente cliente = clienteRepository.findById(pedidoDTO.getIdCliente())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        pedido.setCliente(cliente);

        // Actualizar fecha y estado
        pedido.setFechaPedido(pedidoDTO.getFechaPedido());
        pedido.setEstadoPedido(pedidoDTO.isEstadoPedido());

        // Eliminar detalles existentes y restaurar la disponibilidad de los productos
        for (DetallePedido detalle : pedido.getDetallesPedido()) {
            Producto producto = productoRepository.findById(detalle.getIdProducto())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
            producto.setDisponibilidad(producto.getDisponibilidad() + detalle.getCantidad());
            productoRepository.save(producto);
        }
        detallePedidoRepository.deleteAll(pedido.getDetallesPedido());
        pedido.getDetallesPedido().clear();

        double subtotalTotal = 0.0;

        // Procesar y agregar nuevos detalles
        for (ProductoPedidoDTO productoPedidoDTO : pedidoDTO.getProductos()) {
            Producto producto = productoRepository.findById(productoPedidoDTO.getIdProducto())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

            if (producto.getDisponibilidad() < productoPedidoDTO.getCantidad()) {
                throw new IllegalArgumentException("No hay suficiente stock del producto: " + producto.getNombre());
            }

            // Actualizar disponibilidad del producto
            producto.setDisponibilidad(producto.getDisponibilidad() - productoPedidoDTO.getCantidad());
            productoRepository.save(producto);

            // Crear y guardar nuevo detalle del pedido
            DetallePedido detallePedido = new DetallePedido();
            detallePedido.setPedido(pedido);
            detallePedido.setIdProducto(producto.getIdProducto());
            detallePedido.setCantidad(productoPedidoDTO.getCantidad());
            detallePedido.setPrecioUnitario(productoPedidoDTO.getPrecioUnitario());
            detallePedido.setSubtotal(productoPedidoDTO.getCantidad() * productoPedidoDTO.getPrecioUnitario());

            detallePedidoRepository.save(detallePedido);
            pedido.getDetallesPedido().add(detallePedido);

            // Sumar al subtotal total
            subtotalTotal += detallePedido.getSubtotal();
        }

        // Establecer el subtotal total en el pedido
        pedido.setSubtotal(subtotalTotal);
        return pedidoRepository.save(pedido);
    }

}

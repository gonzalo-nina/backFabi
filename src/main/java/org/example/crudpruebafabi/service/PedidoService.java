package org.example.crudpruebafabi.service;

import org.example.crudpruebafabi.dto.PedidoDTO;
import org.example.crudpruebafabi.model.Cliente;
import org.example.crudpruebafabi.model.DetallePedido;
import org.example.crudpruebafabi.model.Pedido;
import org.example.crudpruebafabi.repository.ClienteRepository;
import org.example.crudpruebafabi.repository.DetallePedidoRepository;
import org.example.crudpruebafabi.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
    private DetallePedidoRepository detallePedidoRepository;

    public List<PedidoDTO> listarPedidos() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        return pedidos.stream().map(this::convertirAPedidoDTO).collect(Collectors.toList());
    }

    public Optional<Pedido> obtenerPedidoPorId(Long idPedido) {
        return pedidoRepository.findById(idPedido);
    }

    @Transactional
    public void eliminarPedido(Long idPedido) {
        pedidoRepository.deleteById(idPedido);
    }

    @Transactional
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

    public List<PedidoDTO> listarPedidosConEstadoFalse() {
        List<Pedido> pedidos = pedidoRepository.findByEstadoPedidoFalse();
        return pedidos.stream().map(this::convertirAPedidoDTO).collect(Collectors.toList());
    }

    private PedidoDTO convertirAPedidoDTO(Pedido pedido) {

        return new PedidoDTO(
                pedido.getIdPedido(),
                pedido.getCliente().getIdCliente(),
                pedido.getFechaPedido(),
                pedido.isEstadoPedido(),
                pedido.getSubtotal());
    }

    @Transactional
    public Pedido guardarPedido(PedidoDTO pedidoDTO) {
        // Verificar y obtener cliente
        Cliente cliente = clienteRepository.findById(pedidoDTO.getIdCliente())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Cliente con ID " + pedidoDTO.getIdCliente() + " no existe."));

        // Crear y guardar el pedido
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setFechaPedido(LocalDate.now());
        pedido.setEstadoPedido(false);
        pedido.setSubtotal(0.0);
        Pedido nuevoPedido = pedidoRepository.save(pedido);
        return nuevoPedido;
    }

    @Transactional
    public Pedido actualizarSubtotalPedido(Long idPedido, PedidoDTO pedidoDTO) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Pedido con ID " + idPedido + " no existe."));
        pedido.setSubtotal(pedidoDTO.getSubtotal());
        Pedido pedidoActualizado = pedidoRepository.save(pedido);
        return pedidoActualizado;
    }

}

package org.example.crudpruebafabi.controller;

import org.example.crudpruebafabi.model.DetallePedido;
import org.example.crudpruebafabi.service.DetallePedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalles-pedido")
public class DetallePedidoController {

    @Autowired
    private DetallePedidoService detallePedidoService;

    @PostMapping
    public ResponseEntity<DetallePedido> guardarDetallePedido(@RequestBody DetallePedido detallePedido) {
        try {
            // Validamos que el pedido esté asociado
            if (detallePedido.getPedido() == null || detallePedido.getPedido().getIdPedido() == null) {
                throw new IllegalArgumentException("El detalle de pedido debe tener un pedido asociado.");
            }

            DetallePedido nuevoDetalle = detallePedidoService.guardarDetallePedido(detallePedido);
            return ResponseEntity.ok(nuevoDetalle);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping
    public List<DetallePedido> listarDetallesPedidos() {
        return detallePedidoService.listarDetallesPedidos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetallePedido> obtenerDetallePedidoPorId(@PathVariable Long id) {
        return detallePedidoService.obtenerDetallePedidoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDetallePedido(@PathVariable Long id) {
        if (!detallePedidoService.obtenerDetallePedidoPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        detallePedidoService.eliminarDetallePedido(id);
        return ResponseEntity.noContent().build();
    }
}

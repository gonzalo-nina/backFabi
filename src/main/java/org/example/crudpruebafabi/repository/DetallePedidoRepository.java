package org.example.crudpruebafabi.repository;

import org.example.crudpruebafabi.model.DetallePedido;
import org.example.crudpruebafabi.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {
    List<DetallePedido> findByPedido(Pedido pedido);
}
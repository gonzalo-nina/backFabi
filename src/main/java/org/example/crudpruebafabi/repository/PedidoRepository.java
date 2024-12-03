package org.example.crudpruebafabi.repository;

import org.example.crudpruebafabi.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}

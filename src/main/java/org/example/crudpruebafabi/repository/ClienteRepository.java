package org.example.crudpruebafabi.repository;

import org.example.crudpruebafabi.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}

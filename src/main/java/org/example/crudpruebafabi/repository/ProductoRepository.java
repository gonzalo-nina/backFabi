package org.example.crudpruebafabi.repository;

import org.example.crudpruebafabi.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
}

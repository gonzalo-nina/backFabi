package org.example.crudpruebafabi.repository;

import org.example.crudpruebafabi.model.Catalogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogoRepository extends JpaRepository<Catalogo, Long> {
}

package org.example.crudpruebafabi.repository;

import org.example.crudpruebafabi.model.Rol;
import org.example.crudpruebafabi.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RolRepository extends JpaRepository<Rol,Long> {
    Optional<Rol> findByNombreRol(Role rol);
}

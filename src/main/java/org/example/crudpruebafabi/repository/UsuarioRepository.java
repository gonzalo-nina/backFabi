package org.example.crudpruebafabi.repository;

import org.example.crudpruebafabi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsuarioAndClave(String usuario, String clave);
}

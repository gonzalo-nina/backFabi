package org.example.crudpruebafabi.service;

import org.example.crudpruebafabi.dto.UsuarioDTO;
import org.example.crudpruebafabi.model.Usuario;
import org.example.crudpruebafabi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public interface UsuarioService {

    public UserDetailsService userDetailsService();

    public List<UsuarioDTO> obtenerUsuarios();

    public UsuarioDTO actualizarUsuario(Long id, UsuarioDTO usuarioDTO);

    public boolean deshabilitarUsuario(Long id);


}

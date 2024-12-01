package org.example.crudpruebafabi.service.impl;

import org.example.crudpruebafabi.dto.UsuarioDTO;
import org.example.crudpruebafabi.model.Usuario;
import org.example.crudpruebafabi.repository.UsuarioRepository;
import org.example.crudpruebafabi.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                return usuarioRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
            }
        };
    }

    public List<UsuarioDTO> obtenerUsuarios() {
        return usuarioRepository.findAll().stream().map(usuario -> {
            UsuarioDTO dto = new UsuarioDTO();
            dto.setId(usuario.getId());
            dto.setUsuario(usuario.getUsuario());
            dto.setEmail(usuario.getEmail());
            dto.setClave(usuario.getPassword());
            return dto;
        }).collect(Collectors.toList());
    }

    public UsuarioDTO actualizarUsuario(Long id, UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setUsuario(usuarioDTO.getUsuario());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setPassword(new BCryptPasswordEncoder().encode(usuarioDTO.getClave()));
        usuarioRepository.save(usuario);

        return usuarioDTO;
    }

    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

}
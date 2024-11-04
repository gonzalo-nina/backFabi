package org.example.crudpruebafabi.service;

import org.example.crudpruebafabi.dto.UsuarioDTO;
import org.example.crudpruebafabi.model.Usuario;
import org.example.crudpruebafabi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioDTO crearUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setUsuario(usuarioDTO.getUsuario());
        usuario.setClave(usuarioDTO.getClave());
        usuarioRepository.save(usuario);

        usuarioDTO.setId(usuario.getId());
        return usuarioDTO;
    }

    public List<UsuarioDTO> obtenerUsuarios() {
        return usuarioRepository.findAll().stream().map(usuario -> {
            UsuarioDTO dto = new UsuarioDTO();
            dto.setId(usuario.getId());
            dto.setUsuario(usuario.getUsuario());
            dto.setClave(usuario.getClave());
            return dto;
        }).collect(Collectors.toList());
    }

    public UsuarioDTO actualizarUsuario(Long id, UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setUsuario(usuarioDTO.getUsuario());
        usuario.setClave(usuarioDTO.getClave());
        usuarioRepository.save(usuario);

        return usuarioDTO;
    }

    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}

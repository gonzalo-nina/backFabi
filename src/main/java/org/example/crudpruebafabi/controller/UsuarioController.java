package org.example.crudpruebafabi.controller;

import org.example.crudpruebafabi.dto.UsuarioDTO;
import org.example.crudpruebafabi.model.Usuario;
import org.example.crudpruebafabi.repository.UsuarioRepository;
import org.example.crudpruebafabi.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioDTO usuarioDTO) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsuarioAndClave(usuarioDTO.getUsuario(), usuarioDTO.getClave());
        if (usuarioOpt.isPresent()) {
            // Puedes devolver un token JWT aquí si lo deseas
            return ResponseEntity.ok(usuarioDTO); // O devuelve información relevante del usuario
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        }
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> crearUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO nuevoUsuario = usuarioService.crearUsuario(usuarioDTO);
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }


    @GetMapping
    public List<UsuarioDTO> obtenerUsuarios() {
        return usuarioService.obtenerUsuarios();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizarUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO usuarioActualizado = usuarioService.actualizarUsuario(id, usuarioDTO);
        return new ResponseEntity<>(usuarioActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
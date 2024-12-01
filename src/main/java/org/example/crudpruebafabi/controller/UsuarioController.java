package org.example.crudpruebafabi.controller;

import jakarta.validation.Valid;
import org.example.crudpruebafabi.agregates.request.SignUpRequest;
import org.example.crudpruebafabi.dto.UsuarioDTO;
import org.example.crudpruebafabi.model.Usuario;
import org.example.crudpruebafabi.service.AuthenticationService;
import org.example.crudpruebafabi.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/createUser")
    public ResponseEntity<Usuario> signUpUser(@RequestBody @Valid SignUpRequest signUpRequest){
        return new ResponseEntity<>(authenticationService.signUpUser(signUpRequest), HttpStatus.CREATED);
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
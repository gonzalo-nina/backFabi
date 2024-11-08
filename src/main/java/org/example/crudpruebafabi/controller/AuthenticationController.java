package org.example.crudpruebafabi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.crudpruebafabi.agregates.request.SignInRequest;
import org.example.crudpruebafabi.agregates.request.SignUpRequest;
import org.example.crudpruebafabi.agregates.response.AuthenticationResponse;
import org.example.crudpruebafabi.model.Usuario;
import org.example.crudpruebafabi.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/autenticacion")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    //Crear Admin
    @PostMapping("/createAdmin")
    public ResponseEntity<Usuario> signUpAdmin(@RequestBody @Valid SignUpRequest signUpRequest){
        return new ResponseEntity<>(authenticationService.signUpAdmin(signUpRequest), HttpStatus.CREATED);
    }
    //Crear Usuario
    @PostMapping("/createUser")
    public ResponseEntity<Usuario> signUpUser(@RequestBody @Valid SignUpRequest signUpRequest){
        return new ResponseEntity<>(authenticationService.signUpUser(signUpRequest), HttpStatus.CREATED);
    }
    //Iniciar Sesion
    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> signin(@RequestBody @Valid SignInRequest signInRequest){
        return new ResponseEntity<>(authenticationService.signin(signInRequest), HttpStatus.OK);
    }

}

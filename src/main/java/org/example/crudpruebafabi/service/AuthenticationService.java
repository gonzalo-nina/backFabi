package org.example.crudpruebafabi.service;

import org.example.crudpruebafabi.agregates.request.SignInRequest;
import org.example.crudpruebafabi.agregates.request.SignUpRequest;
import org.example.crudpruebafabi.agregates.response.AuthenticationResponse;
import org.example.crudpruebafabi.model.Usuario;

public interface AuthenticationService {
    Usuario signUpAdmin(SignUpRequest signUpRequest);
    Usuario signUpUser(SignUpRequest signUpRequest);
    AuthenticationResponse signin(SignInRequest signInRequest);
}

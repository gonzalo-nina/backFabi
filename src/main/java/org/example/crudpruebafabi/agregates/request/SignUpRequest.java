package org.example.crudpruebafabi.agregates.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SignUpRequest(
        @NotBlank String usuario,
        @Email String email,
        @NotBlank String clave
) {
}

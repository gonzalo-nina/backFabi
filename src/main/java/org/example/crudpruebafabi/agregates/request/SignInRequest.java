package org.example.crudpruebafabi.agregates.request;

import jakarta.validation.constraints.NotBlank;

public record SignInRequest(
        @NotBlank String email,
        @NotBlank String clave
        ) {
}

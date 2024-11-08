package org.example.crudpruebafabi.agregates.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"username","message","jwt","status"})
public record AuthenticationResponse(
        String email,
        String message,
        String jwt,
        boolean status
) {
}

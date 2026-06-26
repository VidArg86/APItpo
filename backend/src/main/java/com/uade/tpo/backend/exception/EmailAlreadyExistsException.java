package com.uade.tpo.backend.exception;
 
public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String email) {
        super("El email '" + email + "' ya está registrado.");
    }
}

package com.uade.tpo.e_commerce3.exception;
 
public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String email) {
        super("El email '" + email + "' ya está registrado.");
    }
}

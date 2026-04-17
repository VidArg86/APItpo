package com.uade.tpo.e_commerce3.exception;

public class NotFoundImageException extends RuntimeException {
    public NotFoundImageException(String message) {
        super("El imagen no encontrada." + message );
    }
}

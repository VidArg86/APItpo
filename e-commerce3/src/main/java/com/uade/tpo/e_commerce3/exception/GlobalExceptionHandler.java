package com.uade.tpo.e_commerce3.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException ex) {
    Map<String, Object> body = new HashMap<>();
    body.put("error", "Recurso no encontrado");
    body.put("mensaje", ex.getMessage());
    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(InsufficientStockException.class)
  public ResponseEntity<Object> handleInsufficientStock(InsufficientStockException ex) {
    Map<String, Object> body = new HashMap<>();
    body.put("error", "Error de Stock");
    body.put("mensaje", ex.getMessage());
    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(EmailAlreadyExistsException.class)
  public ResponseEntity<Object> handleEmailAlreadyExists(EmailAlreadyExistsException ex) {
    Map<String, Object> body = new HashMap<>();
    body.put("error", "Email duplicado");
    body.put("mensaje", ex.getMessage());
    return new ResponseEntity<>(body, HttpStatus.CONFLICT); // 409
  }

  @ExceptionHandler(UserAlreadyExistsException.class)
  public ResponseEntity<Object> handleUserAlreadyExists(UserAlreadyExistsException ex) {
    Map<String, Object> body = new HashMap<>();
    body.put("error", "Usuario duplicado");
    body.put("mensaje", ex.getMessage());
    return new ResponseEntity<>(body, HttpStatus.CONFLICT); // 409
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException ex) {
    Map<String, Object> body = new HashMap<>();
    body.put("error", "Petición inválida");
    body.put("mensaje", ex.getMessage());
    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST); // 400
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleGlobalException(Exception ex) {
    Map<String, Object> body = new HashMap<>();
    body.put("error", "Error interno del servidor");
    body.put("mensaje", "Ocurrió un error inesperado. Por favor, contacte al soporte.");
    // Opcional: imprimir el stacktrace en consola para debugging interno
    ex.printStackTrace(); 
    return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR); // 500
  }
}
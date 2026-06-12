package com.TPIntegradorJPA.TPIntegradorJPA.exceptions;

import com.TPIntegradorJPA.TPIntegradorJPA.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice // <-- ¡La magia! Esto lo convierte en el gerente global
public class GlobalExceptionHandler {

    // Le decimos: "Si alguien tira una ResourceNotFoundException, ejecutá este método"
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> manejarRecursoNoEncontrado(ResourceNotFoundException ex) {

        // 1. Armamos el "papelito prolijo" con el mensaje de la excepción
        ErrorResponseDTO errorDTO = new ErrorResponseDTO(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.name(), // Imprime "NOT_FOUND"
                LocalDateTime.now()
        );

        // 2. Lo devolvemos a Postman con el código 404
        return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
    }
}
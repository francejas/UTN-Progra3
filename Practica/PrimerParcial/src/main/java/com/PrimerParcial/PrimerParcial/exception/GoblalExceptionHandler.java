package com.PrimerParcial.PrimerParcial.exception;

import com.PrimerParcial.PrimerParcial.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GoblalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> manejarNoEncontrado (ResourceNotFoundException ex){
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(ex.getMessage(), HttpStatus.NOT_FOUND.name(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CapturaInvalidaException.class)
    public ResponseEntity<ErrorResponseDTO> manejarCapturaInvalida(CapturaInvalidaException ex){
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(ex.getMessage(), HttpStatus.BAD_REQUEST.name(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }
}

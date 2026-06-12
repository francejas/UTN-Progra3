package com.ParcialCejasFrancisco.exception;

import com.ParcialCejasFrancisco.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ErrorResponseDTO> manejarNoEncontrado (ResourceNotFound ex){
        ErrorResponseDTO error = new ErrorResponseDTO(ex.getMessage(), HttpStatus.NOT_FOUND.name(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PrestamoInvalidoException.class)
    public ResponseEntity<ErrorResponseDTO> manejarPrestamoInvalido(PrestamoInvalidoException ex){
        ErrorResponseDTO error = new ErrorResponseDTO(ex.getMessage(),HttpStatus.BAD_REQUEST.name(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


}

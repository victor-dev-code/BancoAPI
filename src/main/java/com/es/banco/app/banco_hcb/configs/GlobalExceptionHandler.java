package com.es.banco.app.banco_hcb.configs;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.es.banco.app.banco_hcb.dtos.responses.ErrorResponseDTO;
import com.es.banco.app.banco_hcb.exceptions.ClientNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenralException(Exception e) {
        ErrorResponseDTO error = new ErrorResponseDTO(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            e.getMessage(),
            "Ha ocurrido un error inesperado, comuniquese con el administrador"
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    
    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleClientNotFound(ClientNotFoundException ec) {
        ErrorResponseDTO error = new ErrorResponseDTO(
            HttpStatus.NOT_FOUND.value(),
            ec.getMessage(),
            "Cliente no encontrado en la base de datos"
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}

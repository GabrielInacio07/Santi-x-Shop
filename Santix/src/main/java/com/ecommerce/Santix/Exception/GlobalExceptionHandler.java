package com.ecommerce.Santix.Exception;

import com.ecommerce.Santix.DTOs.Error.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFound.class)
    public ResponseEntity<ErrorDTO> entityNotFound(EntityNotFound exception){
        ErrorDTO error = ErrorDTO.builder()
               .code("ENTITY_NOT_FOUND")
               .message(exception.getMessage())
               .timestamp(LocalDateTime.now())
               .build();
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> userIllegalArgument(IllegalArgumentException exception){
        ErrorDTO error = ErrorDTO.builder()
                .code("INVALID_ARGUMENT")
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorDTO> notAuthorized (UnauthorizedException exception){
        ErrorDTO error = ErrorDTO.builder()
                .code("NOT_AUTHORIZED")
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

}

package com.ecommerce.Santix.Exception;

import com.ecommerce.Santix.DTOs.Error.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFound.class)
    public ResponseEntity<ErrorDTO> entityNotFound(EntityNotFound exception) {
        ErrorDTO error = ErrorDTO.builder()
                .code(404)
                .message("NOT_FOUND")
                .details(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> userIllegalArgument(IllegalArgumentException exception) {
        ErrorDTO error = ErrorDTO.builder()
                .code(400)
                .message("BAD_REQUEST")
                .details(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorDTO> notAuthorized(UnauthorizedException exception) {
        ErrorDTO error = ErrorDTO.builder()
                .code(403)
                .message("NOT_AUTHORIZED")
                .details(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDTO> badCredentials(BadCredentialsException exception) {
        ErrorDTO error = ErrorDTO.builder()
                .code(401)
                .message("BAD_CREDENTIALS")
                .details("Email ou senha inválidos")
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleGeneric(Exception exception) {
        ErrorDTO error = ErrorDTO.builder()
                .code(500)
                .message("SERVER_ERROR")
                .details(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}

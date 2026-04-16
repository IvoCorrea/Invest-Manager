package com.ivocorrea.investmanager.exception;

import com.ivocorrea.investmanager.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> illegalArgumentsHandler(IllegalArgumentException err) {
        ErrorResponseDTO errorMessage = new ErrorResponseDTO(
                err.getMessage(),
                400,
                LocalDateTime.now().toString()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDTO> globalRuntimeHandler(RuntimeException err) {
        ErrorResponseDTO errorMessage = new ErrorResponseDTO(
                err.getMessage(),
                500,
                LocalDateTime.now().toString()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDTO> accessDeniedHandler(AccessDeniedException err) {
        ErrorResponseDTO errorMessage = new ErrorResponseDTO(
                err.getMessage(),
                403,
                LocalDateTime.now().toString()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorMessage);
    }
}

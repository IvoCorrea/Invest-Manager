package com.ivocorrea.investmanager.exception;

import com.ivocorrea.investmanager.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class UserExceptionHandler {

    public static class NotFoundException extends RuntimeException {
        public NotFoundException() {
            super("User not found");
        }
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> UserNotFoundHandler(NotFoundException err) {
        ErrorResponseDTO response = new ErrorResponseDTO(
                err.getMessage(),
                404,
                LocalDateTime.now().toString()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}

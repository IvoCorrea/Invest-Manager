package com.ivocorrea.investmanager.dto;

public record ErrorResponseDTO(String message, int status, String timestamp) {
}

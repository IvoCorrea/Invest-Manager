package com.ivocorrea.investmanager.controller.dto;

public record ErrorResponseDTO(String message, int status, String timestamp) {
}

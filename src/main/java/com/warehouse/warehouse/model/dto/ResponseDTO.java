package com.warehouse.warehouse.model.dto;

import org.springframework.http.HttpStatus;

public class ResponseDTO {
    private final HttpStatus status;
    private final String message;

    public ResponseDTO(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}

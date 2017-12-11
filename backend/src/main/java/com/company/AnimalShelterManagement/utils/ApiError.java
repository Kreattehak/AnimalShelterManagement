package com.company.AnimalShelterManagement.utils;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApiError {

    private LocalDateTime timestamp;
    private HttpStatus status;
    private String message;

    private ApiError() {
        timestamp = LocalDateTime.now();
    }

    public ApiError(HttpStatus status) {
        this();
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ApiError{" +
                "timestamp=" + timestamp +
                ", status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}

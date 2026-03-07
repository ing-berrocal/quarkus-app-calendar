package com.calendario.app.exception;

public record ErrorMapper(String message) {
    public static ErrorMapper createInstance(String message) {
        return new ErrorMapper(message);
    }
}

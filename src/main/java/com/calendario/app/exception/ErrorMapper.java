package com.calendario.app.exception;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorMapper(String message, List<FieldError> errors) {

    public record FieldError(String field, String message) {}

    public static ErrorMapper createInstance(String message) {
        return new ErrorMapper(message, null);
    }

    public static ErrorMapper createValidationInstance(String message, List<FieldError> errors) {
        return new ErrorMapper(message, errors);
    }
}

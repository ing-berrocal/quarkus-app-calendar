package com.calendario.app.controller.exceptionmapper;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.calendario.app.exception.ErrorMapper;
import com.calendario.app.exception.ErrorMapper.FieldError;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    private static final Logger logger = LoggerFactory.getLogger(ConstraintViolationExceptionMapper.class);

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        logger.warn("Validation failed: {}", exception.getMessage());

        List<FieldError> fieldErrors = exception.getConstraintViolations().stream()
                .map(violation -> {
                    String path = violation.getPropertyPath().toString();
                    String field = path.contains(".") ? path.substring(path.lastIndexOf('.') + 1) : path;
                    return new FieldError(field, violation.getMessage());
                })
                .collect(Collectors.toList());

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorMapper.createValidationInstance("Validation failed", fieldErrors))
                .build();
    }
}

package com.calendario.app.controller.exceptionmapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.calendario.app.exception.AppointmentException;
import com.calendario.app.exception.ErrorMapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ApponinmentAppExceptionMapper implements ExceptionMapper<AppointmentException> {

    private static final Logger logger = LoggerFactory.getLogger(ApponinmentAppExceptionMapper.class);

    @Override
    public Response toResponse(AppointmentException exception) {
        logger.error("An error occurred: ", exception);
        if (exception instanceof AppointmentException) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(ErrorMapper.createInstance(exception.getMessage()))
                    .build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ErrorMapper.createInstance("An unexpected error occurred"))
                .build();
    }
}
package com.calendario.app.controller;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.calendario.app.service.ScheduleManager;

import io.vertx.core.json.JsonObject;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Tuple;

@Path("/schedule")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ScheduleAPI {
    
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    private ScheduleManager schedulerManager;

    @GET
    public Response getSchedule(
        @QueryParam("queryDate")
        LocalDate queryDate,
        @QueryParam("serviceId")
        Long serviceId
    ){
        JsonObject json = JsonObject.of("null", queryDate)
            .put("serviceInstanceId", serviceId);

        logger.debug("Inicia proceso de consulta");

        Tuple[] tuple = schedulerManager.getScheduleAvailable(queryDate, serviceId);

        return Response.ok(tuple).build();
    }
}

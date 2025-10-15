package com.calendario.app.controller;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;


@OpenAPIDefinition(
    info = @Info(
        title = "Appointment API",
        version = "1.0.0",
        description = "API para la gestión de citas.",
        contact = @Contact(
            name = "Soporte Calendario",
            email = "soporte@calendario.com",
            url="http://url.com"
        )
    ),
    tags = {
            @Tag(name="widget", description="Widget operations."),
            @Tag(name="gasket", description="Operations related to gaskets")
    }
)
@ApplicationPath("/api")
public class AppointmentApiApplication extends Application {
    // Puedes agregar configuraciones adicionales aquí si es necesario
}
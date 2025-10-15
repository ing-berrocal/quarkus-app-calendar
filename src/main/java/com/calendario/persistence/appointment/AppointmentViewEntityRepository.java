package com.calendario.persistence.appointment;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AppointmentViewEntityRepository implements PanacheRepository<AppointmentViewEntity> {
    
}

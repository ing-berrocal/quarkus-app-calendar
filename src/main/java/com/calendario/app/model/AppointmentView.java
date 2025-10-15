package com.calendario.app.model;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentView(
                Long id,
                LocalDate date,
                LocalTime time,
                Long serviceInstanceId,
                Long customerId,
                String fullName,
                String serviceName,
                boolean confirmed) {

        public String getTimeFormatted() {
                return time.toString();
        }
}

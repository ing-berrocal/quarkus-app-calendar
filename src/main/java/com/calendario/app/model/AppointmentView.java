package com.calendario.app.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public record AppointmentView(
                Long id,
                LocalDate date,
                LocalTime appointmentTime,
                Long serviceInstanceId,
                Long customerId,
                String fullName,
                String email,
                String telephoneNumber,
                String serviceName,
                boolean confirmed) {

                            static DateTimeFormatter formatter12Hour = DateTimeFormatter.ofPattern("hh:mm a");

        public Integer getTime() {
                return appointmentTime.toSecondOfDay() / 60;
        }

        public String getTimeFormatted() {
                return appointmentTime.format(formatter12Hour);
        }
}

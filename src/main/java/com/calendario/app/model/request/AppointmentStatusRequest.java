package com.calendario.app.model.request;

public class AppointmentStatusRequest {

    private Long appointmentId;

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }
    public Long getAppointmentId() {
        return appointmentId;
    }
}
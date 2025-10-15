package com.calendario.app.model.request;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class AppointmentRequest {

    @NotNull
    private LocalDate requestDate;
    @NotNull
    private LocalTime requestTime;
    @NotNull
    private Long serviceInstanceId;
    @Positive
    private Long customerId;
    
    private LocalTime requestTimeEnd;

    public LocalDate getRequestDate() {
        return requestDate;
    }
    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }
    public LocalTime getRequestTime() {
        return requestTime;
    }
    public void setRequestTime(LocalTime requestTime) {
        this.requestTime = requestTime;
    }
    public Long getServiceInstanceId() {
        return serviceInstanceId;
    }
    public void setServiceInstanceId(Long serviceInstanceId) {
        this.serviceInstanceId = serviceInstanceId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public LocalTime getRequestTimeEnd() {
        return requestTimeEnd;
    }

    public void setRequestTimeEnd(LocalTime requestTimeEnd) {
        this.requestTimeEnd = requestTimeEnd;
    }
}
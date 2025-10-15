package com.calendario.persistence.appointment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@NamedQueries({
    @NamedQuery(name = "Appointment.byDate", query = "from AppointmentEntity where appointmentDate = :queryDate"),
    @NamedQuery(name = "Appointment.byRangeDate", query = "from AppointmentEntity where appointmentDate BETWEEN :initDate AND :endDate"),
    @NamedQuery(name = "Appointment.byCountDate", query = "select count(*) from AppointmentEntity where appointmentDate = :queryDate AND appointmentTime = :queryTime AND serviceInstance = :serviceInstanceId"),
    @NamedQuery(name = "Appointment.byStatus", query = "from AppointmentEntity where appointmentDate = :queryDate AND serviceInstance = :serviceInstance AND status = :status")
})
@Table(name = "appointment", schema = "public")
public class AppointmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "service_instance", nullable = false)
    private Long serviceInstance;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "appointment_date", nullable = false)
    private LocalDate appointmentDate;

    @Column(name = "appointment_time", nullable = false)
    private LocalTime appointmentTime;

    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedDate;

    @Column(name = "status", nullable = false, length = 10)
    private String status;

    // Default constructor
    public AppointmentEntity() {}

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getServiceInstance() {
        return serviceInstance;
    }

    public void setServiceInstance(Long serviceInstance) {
        this.serviceInstance = serviceInstance;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
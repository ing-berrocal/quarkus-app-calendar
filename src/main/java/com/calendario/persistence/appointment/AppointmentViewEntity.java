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
        @NamedQuery(name = "AppointmentView.byDate", query = "from AppointmentViewEntity where appointmentDate = :queryDate"),
        @NamedQuery(name = "AppointmentView.byServiceId", query = "from AppointmentViewEntity where appointmentDate = :queryDate AND serviceId = :serviceId"),
        @NamedQuery(name = "AppointmentView.byRangeDate", query = "from AppointmentViewEntity where appointmentDate BETWEEN :initDate AND :endDate"),
        @NamedQuery(name = "AppointmentView.byCountDate", query = "select count(*) from AppointmentViewEntity where appointmentDate = :queryDate AND appointmentTime = :queryTime AND serviceInstance = :serviceInstanceId"),
        @NamedQuery(name = "AppointmentView.byStatus", query = "from AppointmentViewEntity where appointmentDate = :queryDate AND serviceInstance = :serviceInstance AND status = :status")
})
@Table(name = "view_appointment", schema = "public")
public class AppointmentViewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "service_id", nullable = false)
    private Long serviceId;

    @Column(name = "service_instance", nullable = false)
    private Long serviceInstance;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "appointment_date", nullable = false)
    private LocalDate appointmentDate;

    @Column(name = "appointment_time", nullable = false)
    private LocalTime appointmentTime;

    @Column(name = "description", nullable = false, length = 10)
    private String serviceDescription;

    @Column(name = "customer_name", nullable = false, length = 10)
    private String fullname;

    @Column(name = "customer_email", nullable = false, length = 10)
    private String email;

    @Column(name = "customer_telephone", nullable = false, length = 10)
    private String telephoneNumber;

    @Column(name = "status", nullable = false, length = 10)
    private String status;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    // Default constructor
    public AppointmentViewEntity() {
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Long getServiceId() {
        return serviceId;
    }
    
    public Long getServiceInstance() {
        return serviceInstance;
    }

    public void setServiceInstance(Long serviceInstance) {
        this.serviceInstance = serviceInstance;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getFullname() {
        return fullname;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }
    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }
    public String getTelephoneNumber() {
        return telephoneNumber;
    }
}
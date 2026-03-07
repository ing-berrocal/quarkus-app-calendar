package com.calendario.app.service;

import com.calendario.app.exception.AppointmentException;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.calendario.app.interceptor.Logged;
import com.calendario.app.model.Appointment;
import com.calendario.app.model.AppointmentView;
import com.calendario.app.model.Customer;
import com.calendario.domain.model.ServiceInstance;
import com.calendario.persistence.appointment.AppointmentRepository;
import com.calendario.persistence.customer.CustomerRepositoryImpl;
import com.calendario.persistence.service.ServiceRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@Logged
@ApplicationScoped
public class AppointmentManager {

    @Inject
    private CustomerRepositoryImpl customerRepositoryImpl;

    @Inject
    private ServiceRepository serviceRepository;

    @Inject
    private AppointmentRepository appointmentRepository;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public List<AppointmentView> getAppointment(LocalDate queryDate,Optional<Long> serviceId){

        return appointmentRepository.getAppointmentList(queryDate, serviceId,Optional.empty());
    }
    
    public List<AppointmentView> getRangeAppointment(LocalDate initDate, LocalDate endDate) {
        
        return appointmentRepository.getAppointmentList(initDate, endDate);
    }

    public void createAppointment(Appointment appointment) {

        if(Objects.nonNull(appointment.customerId())){
            Customer validatedCustomer = validateCustomer(appointment.customerId());
        }

        ServiceInstance validatedServiceInstance = validateServiceInstance(appointment.serviceInstanceId());

        if(!appointmentRepository.isAvailable(appointment.date(), appointment.time(), appointment.serviceInstanceId())){
            throw new AppointmentException("Appointment is not available");
        }

        appointmentRepository.addAppointment(appointment);
    }

    public void updateAppointment(Appointment appointment) {

        if(Objects.nonNull(appointment.id())){
        }

        appointmentRepository.cancelAppointment(appointment);
    }

    private ServiceInstance validateServiceInstance(Long serviceInstanceId) {
        return serviceRepository.getServiceInstanceById(serviceInstanceId)
        .orElseThrow(() -> new AppointmentException("ServiceInstance doesn't exist"));
    }

    private Customer validateCustomer(Long customerId) {
        // TODO Auto-generated method stub
        return customerRepositoryImpl.findById(customerId)
        .orElseThrow(() -> new AppointmentException("Customer doesn't exist"));
    }

}

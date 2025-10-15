/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.calendario.app.service;

import jakarta.enterprise.context.ApplicationScoped;

import com.calendario.domain.infrastructure.service.ServiceBeanImplementation;
import com.calendario.domain.model.ServiceInstance;
import com.calendario.app.model.Service;
import java.util.List;

/**
 *
 * @author DELL
 */
@ApplicationScoped
public class ServiceManager {

    private final ServiceBeanImplementation serviceBeanImplementation;
    
    public ServiceManager(ServiceBeanImplementation serviceBeanImplementation) {
        this.serviceBeanImplementation = serviceBeanImplementation;
    }

    public List<Service> findAll(){
        return serviceBeanImplementation.getServices();
    }
    
    public Service findById(Long id) {
        return serviceBeanImplementation.getService(id);
    }

    public List<ServiceInstance> findByServiceId(Long id) {
        return serviceBeanImplementation.getInstanceService(id);
    }

    public Service persist(Service service) {
        return serviceBeanImplementation.addItem(service);        
    }

    public Service editService(Service service) {
        return serviceBeanImplementation.edit(service);        
    }
    
}

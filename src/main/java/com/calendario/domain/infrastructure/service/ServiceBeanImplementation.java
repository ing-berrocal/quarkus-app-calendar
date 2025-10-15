
package com.calendario.domain.infrastructure.service;

import com.calendario.persistence.service.ServiceRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

import com.calendario.app.interceptor.Logged;
import com.calendario.app.model.Service;
import com.calendario.domain.model.ServiceInstance;

/**
 *
 * @author DELL
 */
@Logged
@ApplicationScoped
public class ServiceBeanImplementation {

    @Inject
    private ServiceRepository serviceRepository;
    
    public List<Service> getServices() {    
        return serviceRepository.getServices();
    }
    
    public Service getService(Long serviceId) {    
        return serviceRepository.getServiceById(serviceId).orElseThrow();
    }

    public List<ServiceInstance> getInstanceService(Long serviceId) {
        return serviceRepository.getServicesInstanceByServiceId(serviceId);
    }

    public Service addItem(Service service) {
        return serviceRepository.createService(service);
    }

    public Service edit(Service service) {
        return serviceRepository.editServiceDescription(service);
    }
    
}

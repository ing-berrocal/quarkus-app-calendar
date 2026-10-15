/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.calendario.persistence.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.calendario.app.model.Service;
import com.calendario.domain.model.ServiceInstance;


@ApplicationScoped
public class ServiceRepository  {
        
    @Inject
    private ServiceEntityRepository serviceRepository; 

    @Inject
    private ServiceInstanceEntityRepository serviceInstanceRepository; 

    public Optional<Service> getServiceById(Long id){
        return serviceRepository.findByIdOptional(id)
                .map(e->new Service(e.getId(), 
                        e.getTitle(),
                        e.getDescription(), 
                        e.getDurationInMinute(), 
                        e.getServiceConcurrency()));                
    }
    
    public List<ServiceInstance> getServicesInstanceByServiceId(Long id){
        return serviceRepository.findByIdOptional(id)
                .map(e->e.getViewServiceInstance())
                .map(e->
                        e.stream().map(f->new ServiceInstance(f.getId(),f.getEmployeeName(),f.getState()))
                        .collect(Collectors.toList())
                ).orElse(Collections.EMPTY_LIST);                
    }

    public Optional<ServiceInstance> getServiceInstanceById(Long id){
        return serviceInstanceRepository.findByIdOptional(id)
                .map(f->new ServiceInstance(f.getId(),null,null))
            ;                
    }
    
    @Transactional 
    public Service createService(Service serviceModel) {
        
        ServiceEntity serviceEntity = new ServiceEntity();
        serviceEntity.setTitle(serviceModel.title());
        serviceEntity.setDescription(serviceModel.description());
        serviceEntity.setDurationInMinute(serviceModel.durationInMinute());
        serviceEntity.setServiceConcurrency(serviceModel.serviceConcurrency());
        serviceEntity.setState(Boolean.TRUE);
        
        var list = new ArrayList<ServiceInstanceEntity>();
        for(int i=0;i<serviceModel.serviceConcurrency();i++){
            var si = new ServiceInstanceEntity();
            si.setService(serviceEntity);
            si.setState(Boolean.TRUE);
            list.add(si);
        }
        
        serviceEntity.setServiceInstance(list);
        
        serviceRepository.persist(serviceEntity);
        
        return new Service(serviceEntity.getId(), "","", 0, 0);
    }

    public List<Service> getServices() {
        return serviceRepository.findAll()
                .stream()
                .map(e->new Service(e.getId(), 
                e.getTitle(),
                        e.getDescription(), 
                        e.getDurationInMinute(), 
                        e.getServiceConcurrency()))
                .collect(Collectors.toList()); 
    }

    @Transactional
    public Service editServiceDescription(Service service) {
        // TODO Auto-generated method stub
        ServiceEntity byId = serviceRepository.findById(service.serviceId());

        byId.setDescription(service.description());
        byId.setTitle(service.title());
        serviceRepository.persistAndFlush(byId);
        serviceRepository.getEntityManager().detach(byId);

        return new Service(null,null, null, 0, 0);

    }
}

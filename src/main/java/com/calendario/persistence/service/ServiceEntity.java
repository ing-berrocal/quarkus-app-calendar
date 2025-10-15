/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.calendario.persistence.service;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.List;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "service", schema = "public")
public class ServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "description", length = 250)
    private String description;

    @Column(name = "duration_in_minute", nullable = false)
    private Integer durationInMinute;

    @Column(name = "service_concurrency", nullable = false)
    private Integer serviceConcurrency;
    
    @Transient
    @Column(name = "state", nullable = false)
    private Boolean state;    
    
    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServiceInstanceEntity> serviceInstance;

    @OneToMany(mappedBy = "service", fetch = FetchType.LAZY)
    private List<ViewServiceInstanceEntity> viewServiceInstance;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String tittle) {
        this.title = tittle;
    }

    public String getTitle() {
        return title;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDurationInMinute() {
        return durationInMinute;
    }

    public void setDurationInMinute(Integer durationInMinute) {
        this.durationInMinute = durationInMinute;
    }

    public Integer getServiceConcurrency() {
        return serviceConcurrency;
    }

    public void setServiceConcurrency(Integer serviceConcurrency) {
        this.serviceConcurrency = serviceConcurrency;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public void setServiceInstance(List<ServiceInstanceEntity> serviceInstance) {
        this.serviceInstance = serviceInstance;
    }

    public List<ServiceInstanceEntity> getServiceInstance() {
        return serviceInstance;
    }
    
    public void setViewServiceInstance(List<ViewServiceInstanceEntity> viewServiceInstance) {
        this.viewServiceInstance = viewServiceInstance;
    }

    public List<ViewServiceInstanceEntity> getViewServiceInstance() {
        return viewServiceInstance;
    }
}
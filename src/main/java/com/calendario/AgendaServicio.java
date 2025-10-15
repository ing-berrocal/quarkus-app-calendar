/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.calendario;

import com.calendario.app.model.Service;
import com.calendario.domain.infrastructure.service.ServiceBeanImplementation;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import com.calendario.domain.model.ServiceInstance;
import model.Tuple;

/**
 *
 * @author ITPB-RH
 */
public class AgendaServicio {
    
    private final IConfigurationDays configurationDays;
    private final IAppointmentBean appoinment;
    private final ServiceBeanImplementation service;

    Comparator<Tuple> combinedComparator = Comparator.comparing(Tuple::time);
    
    public AgendaServicio(IConfigurationDays configurationDays,
            IAppointmentBean appoinment,
            ServiceBeanImplementation service) {
        this.configurationDays = configurationDays;
        this.appoinment = appoinment;
        this.service = service;
    }

    Tuple[] getAviableHours(Long serviceId, LocalDate queryDate) {

        TreeSet<Tuple> treeSet = new TreeSet<>(combinedComparator);
  
        //var service = new Service("Servicio 1",59,2);
        //Es un dia habil
        if (!isValidDate(queryDate)) {
            //Lanzamos execption
        }
        //Servicio
        Service serviceById = service.getService(serviceId);
        List<ServiceInstance> serviceIntances = service.getInstanceService(serviceId);
                
        int timeDiff = configurationDays.getConfigEndHours().toSecondOfDay() - 
                configurationDays.getConfigInitHours().toSecondOfDay();
        
        int of = configurationDays.getConfigInitHours().toSecondOfDay();       
        
        for (ServiceInstance si : serviceIntances) {

            Map<Integer, Tuple> tuple = new HashMap<>();

            for (int i = 0; i <= (timeDiff/(serviceById.durationInMinute()*60)); i++) {
                tuple.put(
                        (of + ((serviceById.durationInMinute() * 60) * i)),
                        new Tuple(
                                (of + ((serviceById.durationInMinute() * 60) * i)),
                                serviceById.durationInMinute(),
                                serviceById.serviceConcurrency()-1)
                );
            }

            List<Tuple> aviableHoursService = getAviableHoursService(si,
                    tuple, queryDate);

            treeSet.addAll(aviableHoursService);

            if (treeSet.stream().allMatch(Tuple::isValid)) {
                break;
            }
        }

        int[] unavailableHours = getUnavailableHours(queryDate);

        Map<Integer, Tuple> tuple = new HashMap<>();

        treeSet.forEach(t -> {
            tuple.put(t.time(), t);
        });

        for (int i = 0; i < unavailableHours.length; i++) {
            tuple.computeIfPresent(unavailableHours[i], (t, u) -> u.inactive());
        }

        Tuple[] toArray = tuple.values().toArray(new Tuple[0]);

        Arrays.sort(toArray, Comparator.comparingInt(Tuple::time));
        return toArray;
    }

    private List<Tuple> getAviableHoursService(ServiceInstance serviceInstance,
            Map<Integer, Tuple> tuple, LocalDate queryDate) {

        int[] selectedHours = getSelectedHours(serviceInstance.serviceInstanceId(), queryDate);
        //1440

        for (int i = 0; i < selectedHours.length; i++) {
            tuple.computeIfPresent(selectedHours[i], (t, u) -> u.inactive());
        }
        //Tuple[] toArray = tuple.values().toArray(new Tuple[tupleSlots.length]);
        //Arrays.sort(toArray,Comparator.comparingInt(Tuple::time));
        return new ArrayList(tuple.values());
    }

    private int[] getSelectedHours(Long serviceInstanceId, LocalDate queryDate) {
        return appoinment.getSelectedHours(serviceInstanceId,queryDate);
    }

    private int[] getUnavailableHours(LocalDate queryDate) {
        return configurationDays.getConfigHours(Integer.SIZE, queryDate);
    }

    private boolean isValidDate(LocalDate queryDate) {
        return Boolean.FALSE;
    }
}

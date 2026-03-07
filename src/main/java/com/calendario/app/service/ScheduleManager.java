package com.calendario.app.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.calendario.app.model.Service;
import com.calendario.app.model.response.ScheduleResponse;
import com.calendario.domain.infrastructure.service.ServiceBeanImplementation;
import com.calendario.domain.model.ServiceInstance;
import com.calendario.persistence.appointment.AppointmentRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import model.Tuple;

@ApplicationScoped
public class ScheduleManager {

    @Inject
    private AppointmentRepository appointmentRepository;

    @Inject
    private ServiceBeanImplementation serviceBeanImplementation;
    
    @ConfigProperty(name="schedule.config.hour.init.minutes")
    private int timeInit;
    
    Comparator<Tuple> combinedComparator = Comparator.comparing(Tuple::time);

    private Logger logger = LoggerFactory.getLogger(getClass());


    public boolean isAvailable(LocalDate queryDate, LocalTime queryTime, Long serviceInstanceId) {
        // TODO Auto-generated method stub
        return appointmentRepository.isAvailable(queryDate, queryTime, serviceInstanceId);
    }

    public Tuple[] getScheduleAvailable(LocalDate queryDate, Long serviceId) {

        logger.info("Getting available schedule for date {} and service id {}", queryDate, serviceId);
        
        Service serviceById = serviceBeanImplementation.getService(serviceId);
        List<ServiceInstance> serviceInstances = serviceBeanImplementation.getInstanceService(serviceId);

        return getScheduleAvailable(queryDate,serviceById,serviceInstances);
    }
    
    public Tuple[] getScheduleInstanceAvailable(LocalDate queryDate, Long serviceId,Long serviceInstanceId) {
        
        logger.info("Getting available schedule for date {} and instance id {}", queryDate, serviceInstanceId);
        
        Service serviceById = serviceBeanImplementation.getService(serviceId);

        List<ServiceInstance> serviceInstances = serviceBeanImplementation.getInstanceService(serviceId);

        ServiceInstance findServiceInstanceOrError = serviceInstances.stream()
        .filter(t -> t.serviceInstanceId().equals(serviceInstanceId)).findAny().orElseThrow(()-> new RuntimeException("Service Instance not found"));

        return getScheduleAvailable(queryDate,serviceById,List.of(findServiceInstanceOrError));
        
    }

    public Tuple[] getScheduleAvailable(LocalDate queryDate,Service serviceById,List<ServiceInstance> serviceInstances) {

        TreeSet<Tuple> treeSet = new TreeSet<>(combinedComparator);

        // Es un dia habil
        if (!isValidDate(queryDate)) {
            // Lanzamos execption
        }

        int timeDiffInMinutes = 600;

        int tickets = timeDiffInMinutes / serviceById.durationInMinute();

        Map<Integer, Tuple> tupleFromService = IntStream.rangeClosed(0, tickets - 1)
            .mapToObj(i->new Tuple(
                timeInit + (serviceById.durationInMinute() * i),
                serviceById.durationInMinute(),
                serviceInstances.size()
                )
            )
            .collect(Collectors.toMap(Tuple::time,x->x))
            ;


        for (ServiceInstance si : serviceInstances) {

            getAvailableHoursService(si,
                    tupleFromService, queryDate);
        }

        treeSet.addAll(tupleFromService.values());

        //int[] unavailableHours = getUnavailableHours(queryDate);

        Map<Integer, Tuple> tuple = new HashMap<>();

        treeSet.forEach(t -> {
            tuple.put(t.time(), t);
        });

        /*for (int i = 0; i < unavailableHours.length; i++) {
            tuple.computeIfPresent(unavailableHours[i], (t, u) -> u.inactive());
        }*/

        Tuple[] toArray = tuple.values().toArray(new Tuple[0]);

        Arrays.sort(toArray, Comparator.comparingInt(Tuple::time));
        return toArray;
    }

    private void getAvailableHoursService(ServiceInstance si, 
        Map<Integer, Tuple> tuple, LocalDate queryDate) {
        // TODO Auto-generated method stub
        int[] selectedHours = appointmentRepository.getAppointment(queryDate, Optional.ofNullable(si.serviceInstanceId()));

        for (int i = 0; i < selectedHours.length; i++) {

            logger.info("Selected hour: " + selectedHours[i]+" instance: "+si.serviceInstanceId());
            tuple.computeIfPresent(selectedHours[i], (t, u) -> u.removeServiceInstance());
        }
        //Tuple[] toArray = tuple.values().toArray(new Tuple[tupleSlots.length]);
        //Arrays.sort(toArray,Comparator.comparingInt(Tuple::time));
        //return new ArrayList(tuple.values());
    }

    private boolean isValidDate(LocalDate queryDate) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'isValidDate'");
        return Boolean.TRUE;
    }

    public List<ScheduleResponse> getScheduleResponse(LocalDate queryDate, Long serviceId) {
        
        Service serviceById = serviceBeanImplementation.getService(serviceId);
        List<ServiceInstance> serviceInstances = serviceBeanImplementation.getInstanceService(serviceId);

        List<ScheduleResponse> scheduleResponses = new ArrayList<>();

        for (ServiceInstance si : serviceInstances) {
            Tuple[] scheduleAvailable = getScheduleAvailable(queryDate,serviceById,List.of(si));
            ScheduleResponse scheduleResponse = new ScheduleResponse(
                si.serviceInstanceId(),
                si.employeeName(),
                scheduleAvailable
            );
            scheduleResponses.add(scheduleResponse);
        }

        return scheduleResponses;
    }
}

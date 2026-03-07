package com.calendario.persistence.appointment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.calendario.app.model.Appointment;
import com.calendario.app.model.AppointmentView;

import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class AppointmentRepository {
    
    @Inject
    private AppointmentEntityRepository appointmentRepository;
    
    @Inject
    private AppointmentViewEntityRepository appointmentViewRepository;

    @Transactional
    public Appointment addAppointment(Appointment appointment) {

        AppointmentEntity appointmentEntity = new AppointmentEntity();
        appointmentEntity.setServiceInstance(appointment.serviceInstanceId()); 
        appointmentEntity.setCustomerId(appointment.customerId());
        appointmentEntity.setCreatedDate(LocalDateTime.now());
        appointmentEntity.setUpdatedDate(LocalDateTime.now());

        appointmentEntity.setAppointmentDate(appointment.date());
        appointmentEntity.setAppointmentTime(appointment.time());
        appointmentEntity.setStatus("A");

        appointmentRepository.persistAndFlush(appointmentEntity);

        return new Appointment(appointmentEntity.getId(),null,null,null,null);
    }

    @Transactional
    public Appointment cancelAppointment(Appointment appointment) {

        AppointmentEntity byId = appointmentRepository.findById(appointment.id());

        byId.setStatus("C");
        byId.setUpdatedDate(LocalDateTime.now());

        appointmentRepository.persistAndFlush(byId);

        return new Appointment(byId.getId(),null,null,null,null);
    }

    public boolean isAvailable(LocalDate queryDate, LocalTime queryTime, Long serviceInstanceId) {
        return appointmentRepository.count("#Appointment.byCountDate",
        Parameters.with("queryDate", queryDate)
        .and("queryTime", queryTime) 
        .and("serviceInstanceId", serviceInstanceId)) == 0L;
    }

    public int[] getAppointment(LocalDate queryDate, Optional<Long> serviceInstanceId){
        return getStreamAppointment(queryDate, Optional.empty(),serviceInstanceId)
        .map(AppointmentViewEntity::getAppointmentTime)
        .mapToInt(e->e.toSecondOfDay()/60)
        .toArray();
    }

    public List<AppointmentView> getAppointmentList(LocalDate queryDate, Optional<Long> serviceId, Optional<Long> serviceInstanceId){
        return getStreamAppointment(queryDate, serviceId, serviceInstanceId)
        .map(a->new AppointmentView(a.getId(),
        a.getAppointmentDate(),
        a.getAppointmentTime(),a.getServiceInstance(),
        a.getCustomerId(),a.getFullname(),a.getEmail(),a.getTelephoneNumber(),a.getServiceDescription(),
        a.getStatus().equals("A")))
        .toList();
    }
    
    public List<AppointmentView> getAppointmentList(LocalDate initDate, LocalDate endDate) {
        //byRangeDate
        return appointmentViewRepository.list("#AppointmentView.byRangeDate",Parameters.with("initDate", initDate)
        .and("endDate", endDate))
        .stream()
        .map(a->new AppointmentView(a.getId(),a.getAppointmentDate(),a.getAppointmentTime(),a.getServiceInstance(),
        null,a.getFullname(),a.getEmail(),a.getTelephoneNumber(),a.getServiceDescription(),
        true))
        .toList();
    }

    private Stream<AppointmentViewEntity> getStreamAppointment(LocalDate queryDate, Optional<Long> serviceId, Optional<Long> serviceInstanceId){
        return serviceId.map(id->
            appointmentViewRepository.list("#AppointmentView.byServiceId",Parameters.with("queryDate", queryDate).and("serviceId", id))
        )
        .or(()->serviceInstanceId.map(s->
            appointmentViewRepository.list("#AppointmentView.byStatus",Parameters.with("queryDate", queryDate).and("serviceInstance", s)
            .and("status", "A"))
        )).orElseGet(()->
            appointmentViewRepository.list("#AppointmentView.byDate",Parameters.with("queryDate", queryDate))
        )
        .stream();
    }

}

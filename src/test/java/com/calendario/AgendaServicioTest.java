/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.calendario;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import com.calendario.app.model.Service;
import com.calendario.domain.infrastructure.service.ServiceBeanImplementation;
import com.calendario.domain.model.ServiceInstance;
import model.Tuple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

/**
 *
 * @author ITPB-RH
 */
public class AgendaServicioTest {

    IAppointmentBean mockIAppointmentBean = Mockito.mock(IAppointmentBean.class);
    ServiceBeanImplementation mockIServiceBean = Mockito.mock(ServiceBeanImplementation.class);
    IConfigurationDays mockIConfigurationDays = Mockito.mock(IConfigurationDays.class);

    public AgendaServicioTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void get_service_hours() {

        var agendaService = new AgendaServicio(
                new IConfigurationDays() {
            @Override
            public boolean isValidDate(LocalDate queryDate) {
                return Boolean.TRUE;
            }

            @Override
            public int[] getConfigHours(Integer configID, LocalDate queryDate) {
                return new int[]{};
            }

            @Override
            public LocalTime getConfigInitHours() {
                return LocalTime.ofSecondOfDay(28800);
            }

            @Override
            public LocalTime getConfigEndHours() {
                return LocalTime.ofSecondOfDay(64800);
            }

        },
                new IAppointmentBean() {
            @Override
            public int[] getSelectedHours(Long serviceInstanceId, LocalDate queryDate) {
                return new int[]{};
            }
        },
                iservice
        );
        LocalDate date = LocalDate.now();

        Tuple[] hours = agendaService.getAviableHours(1l, date);        

        assertArrayEquals(
                new Tuple[]{
                    new Tuple(28800, 60, 1),
                    new Tuple(32400, 60, 1),
                    new Tuple(36000, 60, 1),
                    new Tuple(39600, 60, 1),
                    new Tuple(43200, 60, 1),
                    new Tuple(46800, 60, 1),
                    new Tuple(50400, 60, 1),
                    new Tuple(54000, 60, 1),
                    new Tuple(57600, 60, 1),
                    new Tuple(61200, 60, 1),
                    new Tuple(64800, 60, 1)
                }, hours);
    }

    @Test
    public void get_service_hours2() {

        var agendaService = new AgendaServicio(iconfig, iappointment, iservice);
        LocalDate date = LocalDate.now();

        Tuple[] hours = agendaService.getAviableHours(1l, date);

        assertArrayEquals(
                new Tuple[]{
                    new Tuple(28800, 60, 1), //8 
                    new Tuple(32400, 60, 1),
                    new Tuple(36000, 60, 1), //10
                    new Tuple(39600, 60, 1),
                    new Tuple(43200, 60, 0), //12
                    new Tuple(46800, 60, 0),
                    new Tuple(50400, 60, 1), //14
                    new Tuple(54000, 60, 1),
                    new Tuple(57600, 60, 1), //16
                    new Tuple(61200, 60, 1),
                    new Tuple(64800, 60, 1) //18
                }, hours);
    }

    @Test
    public void get_service_hours3() {

        LocalDate date = LocalDate.now();

        Mockito.when(
                mockIAppointmentBean.getSelectedHours(
                        ArgumentMatchers.eq(1l),
                        ArgumentMatchers.any(LocalDate.class))
        ).thenReturn(new int[]{
            64800
        });
        Mockito.when(
                mockIAppointmentBean.getSelectedHours(
                        ArgumentMatchers.anyLong(),
                        ArgumentMatchers.any(LocalDate.class))
        ).thenReturn(new int[]{});

        var agendaService = new AgendaServicio(iconfig, mockIAppointmentBean, iservice);

        Tuple[] hours = agendaService.getAviableHours(1l, date);

        assertArrayEquals(
                new Tuple[]{
                    new Tuple(28800, 60, 1), //8 
                    new Tuple(32400, 60, 1),
                    new Tuple(36000, 60, 1), //10
                    new Tuple(39600, 60, 1),
                    new Tuple(43200, 60, 0), //12
                    new Tuple(46800, 60, 0),
                    new Tuple(50400, 60, 1), //14
                    new Tuple(54000, 60, 1),
                    new Tuple(57600, 60, 1), //16
                    new Tuple(61200, 60, 1),
                    new Tuple(64800, 60, 1) //18
                }, hours);
    }

    @Test
    public void get_service_hours4() {

        Mockito.when(
                mockIAppointmentBean.getSelectedHours(
                        ArgumentMatchers.anyLong(),
                        ArgumentMatchers.any(LocalDate.class))
        ).thenReturn(new int[]{
            64800
        });

        var agendaService = new AgendaServicio(iconfig, mockIAppointmentBean, iservice);

        LocalDate date = LocalDate.now();

        Tuple[] hours = agendaService.getAviableHours(1l, date);

        assertArrayEquals(
                new Tuple[]{
                    new Tuple(28800, 60, 1), //8 
                    new Tuple(32400, 60, 1),
                    new Tuple(36000, 60, 1), //10
                    new Tuple(39600, 60, 1),
                    new Tuple(43200, 60, 0), //12
                    new Tuple(46800, 60, 0),
                    new Tuple(50400, 60, 1), //14
                    new Tuple(54000, 60, 1),
                    new Tuple(57600, 60, 1), //16
                    new Tuple(61200, 60, 1),
                    new Tuple(64800, 60, 0) //18
                }, hours);
    }

    @Test
    public void get_service_hours_service() {

        //15
        //900 segundos
        Mockito.when(mockIConfigurationDays.getConfigInitHours())
                .thenReturn(LocalTime.ofSecondOfDay(28800));

        Mockito.when(mockIConfigurationDays.getConfigEndHours())
                .thenReturn(LocalTime.ofSecondOfDay(32400));
        
        Mockito.when(mockIConfigurationDays.getConfigHours(
                ArgumentMatchers.anyInt(),
                ArgumentMatchers.any(LocalDate.class)))
                .thenReturn(new int[]{});

        Mockito.when(
                mockIServiceBean.getService(ArgumentMatchers.anyLong())
        ).thenReturn(new Service(1l, null,"Servicio Prueba 15 min", 15,1 ));
        
        Mockito.when(
                mockIServiceBean.getInstanceService(ArgumentMatchers.anyLong())
        ).thenReturn(Arrays.asList(new ServiceInstance(1l,null,null)));

        Mockito.when(
                mockIAppointmentBean.getSelectedHours(
                        ArgumentMatchers.anyLong(),
                        ArgumentMatchers.any(LocalDate.class))
        ).thenReturn(new int[]{
            64800
        });

        var agendaService = new AgendaServicio(mockIConfigurationDays, mockIAppointmentBean, mockIServiceBean);

        LocalDate date = LocalDate.now();

        Tuple[] hours = agendaService.getAviableHours(1l, date);
        
        //new Tuple(0, 15, 1), //8 
        assertArrayEquals(
                new Tuple[]{
                    new Tuple(28800, 15, 1), //8 
                    new Tuple(29700, 15, 1),
                    new Tuple(30600, 15, 1), //10
                    new Tuple(31500, 15, 1),
                    new Tuple(32400, 15, 1)
                }, hours);
    }

    IConfigurationDays iconfig = new IConfigurationDays() {

        @Override
        public boolean isValidDate(LocalDate queryDate) {
            return Boolean.TRUE;
        }

        @Override
        public int[] getConfigHours(Integer configID, LocalDate queryDate) {
            return new int[]{43200, 46800};
        }

        @Override
        public LocalTime getConfigInitHours() {
            return LocalTime.ofSecondOfDay(28800);
        }

        @Override
        public LocalTime getConfigEndHours() {
            return LocalTime.ofSecondOfDay(64800);
        }
    };

    IAppointmentBean iappointment = new IAppointmentBean() {
        @Override
        public int[] getSelectedHours(Long serviceInstanceId, LocalDate queryDate) {
            return new int[]{43200, 46800};
        }
    };

    ServiceBeanImplementation iservice = new ServiceBeanImplementation() {

        @Override
        public Service getService(Long serviceId) {
            return new Service(1l, "Servicio 1",null, 60, 1);
        }

        @Override
        public List<ServiceInstance> getInstanceService(Long serviceId) {
            return Arrays.asList(
                    new ServiceInstance(1l,null,null),
                    new ServiceInstance(2l,null,null)
            );
        }

        @Override
        public Service addItem(Service service) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    };
}

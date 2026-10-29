package com.calendario.app.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.calendario.app.exception.AppointmentException;
import com.calendario.app.model.Customer;
import com.calendario.persistence.customer.CustomerRepositoryImpl;

class CustomerManagerTest {

    private CustomerRepositoryImpl customerRepository;
    private CustomerManager customerManager;

    @BeforeEach
    void setUp() {
        customerRepository = mock(CustomerRepositoryImpl.class);
        customerManager = new CustomerManager();
        customerManager.customerRepository = customerRepository;
    }

    @Test
    void addCustomer_rejectsDuplicateEmail() {
        Customer customer = new Customer();
        customer.setEmail("existing@example.com");

        when(customerRepository.findByEmail(customer.getEmail()))
                .thenReturn(Optional.of(new Customer()));

        assertThrows(AppointmentException.class, () -> customerManager.addCustomer(customer));
        verify(customerRepository, never()).save(customer);
    }

    @Test
    void updateTercero_preservesExistingEmail() {
        Customer existingCustomer = new Customer();
        existingCustomer.setId(7L);
        existingCustomer.setEmail("original@example.com");
        existingCustomer.setDocumentNumber("12345");
        when(customerRepository.findById(7L)).thenReturn(Optional.of(existingCustomer));

        Customer updates = new Customer();
        updates.setEmail("replacement@example.com");
        updates.setDocumentNumber("12345");

        customerManager.updateTercero(7L, updates);

        verify(customerRepository, never()).findByEmail("replacement@example.com");
        verify(customerRepository).updateSave(org.mockito.ArgumentMatchers.argThat(
                savedCustomer -> "original@example.com".equals(savedCustomer.getEmail())));
    }
}

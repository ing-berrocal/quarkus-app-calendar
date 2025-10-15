package com.calendario.app.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.calendario.app.model.Customer;
import com.calendario.persistence.customer.CustomerRepositoryImpl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CustomerManager {
    
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    CustomerRepositoryImpl customerRepository;

    public long getCount() {
        return customerRepository.getCount();
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerByDocumentNumber(String documentNumber) {
        return customerRepository.findByDocumentNumber(documentNumber);
    }

    @Transactional
    public void addCustomer(Customer customer) {

        logger.info("Adding customer {}", customer);

        if (customerRepository.findByEmail(customer.getEmail()).isPresent() ||
            customerRepository.findByDocumentNumber(customer.getDocumentNumber()).isPresent()) {

            logger.info("Customer founded");

            throw new IllegalArgumentException("Email or Document Number already exists");
        }

        customerRepository.save(customer);
    }

    @Transactional
    public void updateTercero(Long id, Customer tercero) {
        Optional<Customer> existingTerceroOpt = customerRepository.findById(id);
        if (existingTerceroOpt.isEmpty()) {
            throw new IllegalArgumentException("Tercero not found");
        }
        Customer existingTercero = existingTerceroOpt.get();
        if (!existingTercero.getEmail().equals(tercero.getEmail()) && customerRepository.findByEmail(tercero.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        if (!existingTercero.getDocumentNumber().equals(tercero.getDocumentNumber()) && customerRepository.findByDocumentNumber(tercero.getDocumentNumber()).isPresent()) {
            throw new IllegalArgumentException("Document Number already exists");
        }
        existingTercero.setFullname(tercero.getFullname());
        existingTercero.setNames(tercero.getNames());
        existingTercero.setDocumentNumber(tercero.getDocumentNumber());
        existingTercero.setTelephone(tercero.getTelephone());
        existingTercero.setAddress(tercero.getAddress());
        existingTercero.setEmail(tercero.getEmail());
        existingTercero.setState(tercero.getState());
        customerRepository.save(existingTercero);
    }

    @Transactional
    public void deleteTercero(Long id) {
        customerRepository.deleteById(id);
    }
}

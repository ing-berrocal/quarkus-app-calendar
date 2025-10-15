
package com.calendario.persistence.customer;

import com.calendario.app.model.Customer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author DELL
 */
@ApplicationScoped
public class CustomerRepositoryImpl {

    @Inject
    TerceroEntityRepository terceroEntityRepository;
    
    public long getCount() {
        return terceroEntityRepository.count();
    }

    public List<Customer> findAll() {
        return terceroEntityRepository.listAll().stream()
                .map(this::toDomain).toList();
    }

    public Optional<Customer> findById(Long id) {
        return terceroEntityRepository.findByIdOptional(id)
                .map(this::toDomain);
    }

    public Optional<Customer> findByEmail(String email) {
        return terceroEntityRepository
                .find("email", email).firstResultOptional().map(this::toDomain);
    }

    public Optional<Customer> findByDocumentNumber(String documentNumber) {
        return terceroEntityRepository.
                find("documentNumber", documentNumber).firstResultOptional().map(this::toDomain);
    }

    public void save(Customer tercero) {
        terceroEntityRepository.persist(toEntity(tercero));
    }

    public void deleteById(Long id) {
        terceroEntityRepository.delete("id", id);
    }

    private Customer toDomain(TerceroEntity entity) {
        Customer domain = new Customer();
        domain.setId(entity.getId());
        domain.setFullname(entity.getFullName());
        domain.setDocumentNumber(entity.getDocumentNumber());
        domain.setTelephone(entity.getTelephone());
        domain.setAddress(entity.getAddress());
        domain.setEmail(entity.getEmail());
        domain.setState(entity.getState());
        return domain;
    }

    private TerceroEntity toEntity(Customer domain) {
        TerceroEntity entity = new TerceroEntity();
        entity.setId(domain.getId());
        entity.setFullName(domain.getFullname());
        entity.setDocumentNumber(domain.getDocumentNumber());
        entity.setTelephone(domain.getTelephone());
        entity.setAddress(domain.getAddress());
        entity.setEmail(domain.getEmail());
        entity.setState(domain.getState());
        return entity;
    }
}
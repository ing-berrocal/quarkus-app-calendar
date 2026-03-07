
package com.calendario.persistence.customer;

import com.calendario.app.model.Customer;

import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
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
        return terceroEntityRepository.listAll(Sort.by("fullName")).stream()
                .map(this::toDomain).toList();
    }

    public List<Customer> findByText(String text) {
        return terceroEntityRepository.list("lower(telephone) LIKE ?1", "%" + text.toLowerCase() + "%")
                .stream()
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

    public void updateSave(Customer tercero) {

        System.out.println("Updating customer: " + tercero.getId());

        TerceroEntity terceroEntity = terceroEntityRepository.findById(tercero.getId());

        terceroEntity.setFullName(tercero.getFullname());
        terceroEntity.setDocumentNumber(tercero.getDocumentNumber());
        terceroEntity.setTelephone(tercero.getTelephone());
        terceroEntity.setAddress(tercero.getAddress());
        //terceroEntity.setEmail(tercero.getEmail());
        
        /*
        terceroEntityRepository.update("update TerceroEntity SET fullName = :fullName, documentNumber = :documentNumber, telephone = :telephone, address = :address where id = :id",
        Parameters.with("fullName", tercero.getFullname())
        .and("documentNumber", tercero.getDocumentNumber())
        .and("telephone", tercero.getTelephone())
        .and("address", tercero.getAddress())
        .and("id", tercero.getId())
        );
        */            
       terceroEntityRepository.persist(terceroEntity);
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
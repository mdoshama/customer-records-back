package com.example.mdsbackend.customer;

import com.example.mdsbackend.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRespository extends JpaRepository<CustomerEntity, Long > {
    boolean existsByPhoneNumber(String phoneNumber);
    Optional<CustomerEntity> findByPhoneNumber(String phoneNumber);
}

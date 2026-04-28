package com.example.mdsbackend.customer;

import com.example.mdsbackend.dtos.CustomerRequest;
import com.example.mdsbackend.dtos.CustomerResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {

    private final CustomerRespository customerRespository;

    // ── Create ────────────────────────────────────────────────────────────────
    public CustomerResponse createCustomer(CustomerRequest request) {
        if (customerRespository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new RuntimeException("Customer already exists with phone number: " + request.getPhoneNumber());
        }

        CustomerEntity entity = new CustomerEntity();
        entity.setFirstName(request.getFirstName());
        entity.setLastName(request.getLastName());
        entity.setPhoneNumber(request.getPhoneNumber());

        return toResponse(customerRespository.save(entity));
    }

    // ── Get by ID ─────────────────────────────────────────────────────────────
    public CustomerResponse getCustomerById(Long id) {
        CustomerEntity entity = customerRespository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
        return toResponse(entity);
    }

    // ── Get All ───────────────────────────────────────────────────────────────
    public List<CustomerResponse> getAllCustomers() {
        return customerRespository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ── Update ────────────────────────────────────────────────────────────────
    public CustomerResponse updateCustomer(Long id, CustomerRequest request) {

        CustomerEntity entity = customerRespository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));

        // If phone number is changing, make sure the new one isn't already taken
        if (!entity.getPhoneNumber().equals(request.getPhoneNumber())
                && customerRespository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new RuntimeException("Phone number already in use: " + request.getPhoneNumber());
        }


        entity.setFirstName(request.getFirstName());
        entity.setLastName(request.getLastName());
        entity.setPhoneNumber(request.getPhoneNumber());

        return toResponse(customerRespository.save(entity));
    }

    // ── Delete ────────────────────────────────────────────────────────────────
    public void deleteCustomer(Long id) {
        if (!customerRespository.existsById(id)) {
            throw new RuntimeException("Customer not found with id: " + id);
        }
        customerRespository.deleteById(id);
    }

    // ── Search by phone ───────────────────────────────────────────────────────
    public CustomerResponse getCustomerByPhoneNumber(String phoneNumber) {
        CustomerEntity entity = customerRespository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("Customer not found with phone: " + phoneNumber));
        return toResponse(entity);
    }

    // ── Helper ────────────────────────────────────────────────────────────────
    private CustomerResponse toResponse(CustomerEntity entity) {
        return new CustomerResponse(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getPhoneNumber()
        );
    }
}
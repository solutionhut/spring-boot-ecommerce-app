package tech.solutionhut.ecommerce.customer.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import tech.solutionhut.ecommerce.customer.domain.Customer;
import tech.solutionhut.ecommerce.customer.exception.CustomerNotFoundException;
import tech.solutionhut.ecommerce.customer.mapper.CustomerMapper;
import tech.solutionhut.ecommerce.customer.record.CustomerRequest;
import tech.solutionhut.ecommerce.customer.record.CustomerResponse;
import tech.solutionhut.ecommerce.customer.repository.CustomerRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;

    private final CustomerMapper mapper;

    public String createCustomer(@Valid CustomerRequest request) {
        var customer = repository.save(mapper.toCustomer(request));
        return customer.getId();
    }

    public void updateCustomer(@Valid CustomerRequest request) {
        var customer = repository.findById(request.id())
                                 .orElseThrow(() -> new CustomerNotFoundException(String.format("Cannot update customer::Customer with id %s not found", request.id())));

        mergeCustomer(customer, request);
        repository.save(customer);
    }

    private void mergeCustomer(Customer customer, @Valid CustomerRequest request) {
        if (StringUtils.isNotBlank(request.firstname())) {
            customer.setFirstName(request.firstname());
        }

        if (StringUtils.isNotBlank(request.lastname())) {
            customer.setLastName(request.lastname());
        }

        if (StringUtils.isNotBlank(request.email())) {
            customer.setEmail(request.email());
        }

        if (Objects.nonNull(request.address())) {
            customer.setAddress(request.address());
        }
    }

    public List<CustomerResponse> findAllCustomers() {
        return repository.findAll()
                         .stream()
                         .map(mapper::fromCustomer)
                         .collect(Collectors.toList());
    }

    public Boolean existsById(String customerId) {
        return repository.findById(customerId)
                         .isPresent();
    }

    public CustomerResponse findById(String customerId) {
        return repository.findById(customerId)
                         .map(mapper::fromCustomer)
                         .orElseThrow(() -> new CustomerNotFoundException(String.format("Customer with id %s not found", customerId)));
    }

    public void deleteCustomer(String customerId) {
        repository.deleteById(customerId);
    }
}

package tech.solutionhut.ecommerce.customer.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import tech.solutionhut.ecommerce.customer.domain.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {
}

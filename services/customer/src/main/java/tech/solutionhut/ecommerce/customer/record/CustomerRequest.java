package tech.solutionhut.ecommerce.customer.record;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import tech.solutionhut.ecommerce.customer.domain.Address;

public record CustomerRequest(
    String id,

    @NotEmpty(message = "Customer firstname is required")
    String firstname,

    @NotEmpty(message = "Customer firstname is required")
    String lastname,

    @NotEmpty(message = "Customer email is required")
    @Email(message = "Customer email is not a valid email address")
    String email,

    Address address
) {
}

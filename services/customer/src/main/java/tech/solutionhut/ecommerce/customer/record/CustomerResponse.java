package tech.solutionhut.ecommerce.customer.record;

import tech.solutionhut.ecommerce.customer.domain.Address;

public record CustomerResponse(
        String id,

        String firstname,

        String lastname,

        String email,

        Address address
) {
}

package tech.solutionhut.ecommerce.order.record;

public record CustomerResponse(
        String id,
        String firstname,
        String lastname,
        String email
) {
}

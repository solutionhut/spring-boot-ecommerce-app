package tech.solutionhut.ecommerce.customer.record;
import java.util.Map;

public record ErrorResponse(
        Map<String, String> errors
) {
}

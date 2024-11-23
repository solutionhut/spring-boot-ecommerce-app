package tech.solutionhut.ecommerce.product.record;
import java.util.Map;

public record ErrorResponse(
        Map<String, String> errors
) {
}

package tech.solutionhut.ecommerce.order.record;
import java.util.Map;

public record ErrorResponse(
        Map<String, String> errors
) {
}

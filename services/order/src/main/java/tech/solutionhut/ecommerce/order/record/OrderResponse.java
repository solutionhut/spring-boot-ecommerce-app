package tech.solutionhut.ecommerce.order.record;

import tech.solutionhut.ecommerce.order.constants.PaymentMethod;

import java.math.BigDecimal;

public record OrderResponse(
        Integer id,

        String reference,

        BigDecimal amount,

        PaymentMethod paymentMethod,

        String customerId
) {
}

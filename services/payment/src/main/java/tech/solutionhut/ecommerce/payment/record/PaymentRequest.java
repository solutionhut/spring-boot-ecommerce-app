package tech.solutionhut.ecommerce.payment.record;

import tech.solutionhut.ecommerce.payment.constants.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        Integer id,

        BigDecimal amount,

        PaymentMethod paymentMethod,

        Integer orderId,

        String orderReference,

        Customer customer
) {
}

package tech.solutionhut.ecommerce.order.record;

import tech.solutionhut.ecommerce.order.constants.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(

        BigDecimal amount,

        PaymentMethod paymentMethod,

        Integer orderId,

        String orderReference,

        CustomerResponse customer
) {
}

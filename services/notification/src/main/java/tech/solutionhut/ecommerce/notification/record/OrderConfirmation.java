package tech.solutionhut.ecommerce.notification.record;

import tech.solutionhut.ecommerce.notification.constants.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        Customer customer,
        List<Product> products
) {
}

package tech.solutionhut.ecommerce.payment.mapper;

import org.springframework.stereotype.Service;
import tech.solutionhut.ecommerce.payment.domain.Payment;
import tech.solutionhut.ecommerce.payment.record.PaymentRequest;

@Service
public class PaymentMapper {

    public Payment toPayment(PaymentRequest paymentRequest) {
        return Payment.builder()
                      .id(paymentRequest.id())
                      .orderId(paymentRequest.orderId())
                      .paymentMethod(paymentRequest.paymentMethod())
                      .amount(paymentRequest.amount())
                      .build();
    }
}

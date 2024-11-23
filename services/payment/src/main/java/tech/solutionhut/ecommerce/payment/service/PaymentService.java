package tech.solutionhut.ecommerce.payment.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.solutionhut.ecommerce.payment.kafka.NotificationProducer;
import tech.solutionhut.ecommerce.payment.mapper.PaymentMapper;
import tech.solutionhut.ecommerce.payment.record.PaymentNotificationRequest;
import tech.solutionhut.ecommerce.payment.record.PaymentRequest;
import tech.solutionhut.ecommerce.payment.repository.PaymentRepository;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repository;

    private final PaymentMapper mapper;

    private final NotificationProducer notificationProducer;



    public Integer createPayment(@Valid PaymentRequest request) {
        var payment = repository.save(mapper.toPayment(request));
        notificationProducer.send(
                new PaymentNotificationRequest(
                        request.orderReference(),
                        request.amount(),
                        request.paymentMethod(),
                        request.customer().firstname(),
                        request.customer().lastname(),
                        request.customer().email()
                )
        );
        return payment.getId();
    }
}

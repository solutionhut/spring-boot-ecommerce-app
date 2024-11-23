package tech.solutionhut.ecommerce.notification.kafka;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import tech.solutionhut.ecommerce.notification.client.EmailClient;
import tech.solutionhut.ecommerce.notification.constants.NotificationType;
import tech.solutionhut.ecommerce.notification.domain.Notification;
import tech.solutionhut.ecommerce.notification.record.OrderConfirmation;
import tech.solutionhut.ecommerce.notification.record.PaymentConfirmation;
import tech.solutionhut.ecommerce.notification.repository.NotificationRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationRepository repository;

    private final EmailClient emailClient;

    @KafkaListener(topics = "payment-topic")
    public void consumePaymentNotification(PaymentConfirmation paymentConfirmation) throws MessagingException {
        log.info("Consuming the message from payment-topic Topic:: {}", paymentConfirmation);

        repository.save(Notification.builder()
                                    .type(NotificationType.PAYMENT_CONFIRMATION)
                                    .notificationDate(LocalDateTime.now())
                                    .paymentConfirmation(paymentConfirmation)
                                    .build());

        var customerName = paymentConfirmation.customerFirstName() + " " + paymentConfirmation.customerLastName();
        emailClient.sendPaymentEmail(
                paymentConfirmation.customerEmail(),
                customerName,
                paymentConfirmation.amount(),
                paymentConfirmation.orderReference()
        );
    }

    @KafkaListener(topics = "order-topic")
    public void consumeOrderNotification(OrderConfirmation orderConfirmation) throws MessagingException {
        log.info("Consuming the message from order-topic Topic:: {}", orderConfirmation);

        repository.save(Notification.builder()
                                    .type(NotificationType.ORDER_CONFIRMATION)
                                    .notificationDate(LocalDateTime.now())
                                    .orderConfirmation(orderConfirmation)
                                    .build());
        var customerName = orderConfirmation.customer().firstname() + " " + orderConfirmation.customer().lastname();
        emailClient.sendOrderEmail(
                orderConfirmation.customer().email(),
                customerName,
                orderConfirmation.totalAmount(),
                orderConfirmation.orderReference(),
                orderConfirmation.products()
        );
    }
}

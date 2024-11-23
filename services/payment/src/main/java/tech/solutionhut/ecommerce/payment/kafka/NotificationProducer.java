package tech.solutionhut.ecommerce.payment.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import tech.solutionhut.ecommerce.payment.record.PaymentNotificationRequest;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationProducer {

    private final KafkaTemplate<String, PaymentNotificationRequest> kafkaTemplate;

    public void send(PaymentNotificationRequest request) {
        log.info("Sending notification request <{}>", request);
        Message<PaymentNotificationRequest> message = MessageBuilder.withPayload(request)
                                                                    .setHeader(KafkaHeaders.TOPIC, "payment-topic")
                                                                    .build();
        kafkaTemplate.send(message);
    }

}

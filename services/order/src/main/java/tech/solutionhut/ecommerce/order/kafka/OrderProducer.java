package tech.solutionhut.ecommerce.order.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import tech.solutionhut.ecommerce.order.record.OrderConfirmation;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderProducer {

    private final KafkaTemplate<String, OrderConfirmation> kafkaTemplate;

    public void sendOrderConfirmation(OrderConfirmation confirmation) {
        log.info("Sending order confirmation");

        Message<OrderConfirmation> message = MessageBuilder.withPayload(confirmation)
                                                           .setHeader(KafkaHeaders.TOPIC, "order-topic")
                                                           .build();

        kafkaTemplate.send(message);
        log.info("Order confirmation sent");
    }
}
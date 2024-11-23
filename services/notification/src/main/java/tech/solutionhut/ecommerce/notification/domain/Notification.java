package tech.solutionhut.ecommerce.notification.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import tech.solutionhut.ecommerce.notification.constants.NotificationType;
import tech.solutionhut.ecommerce.notification.record.OrderConfirmation;
import tech.solutionhut.ecommerce.notification.record.PaymentConfirmation;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Document
public class Notification {

    @Id
    private String id;

    private NotificationType type;

    private LocalDateTime notificationDate;

    private OrderConfirmation orderConfirmation;

    private PaymentConfirmation paymentConfirmation;


}

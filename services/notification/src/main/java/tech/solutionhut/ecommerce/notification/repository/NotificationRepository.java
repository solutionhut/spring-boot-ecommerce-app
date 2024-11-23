package tech.solutionhut.ecommerce.notification.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import tech.solutionhut.ecommerce.notification.domain.Notification;

public interface NotificationRepository extends MongoRepository<Notification, String> {

}

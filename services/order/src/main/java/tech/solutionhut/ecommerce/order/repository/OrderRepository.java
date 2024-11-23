package tech.solutionhut.ecommerce.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.solutionhut.ecommerce.order.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}

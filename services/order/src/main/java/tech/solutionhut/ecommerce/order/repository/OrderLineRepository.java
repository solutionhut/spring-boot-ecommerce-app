package tech.solutionhut.ecommerce.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.solutionhut.ecommerce.order.domain.OrderLine;
import tech.solutionhut.ecommerce.order.record.OrderLineResponse;

import java.util.List;

public interface OrderLineRepository extends JpaRepository<OrderLine, Integer> {
    List<OrderLine> findAllByOrderId(Integer orderId);
}

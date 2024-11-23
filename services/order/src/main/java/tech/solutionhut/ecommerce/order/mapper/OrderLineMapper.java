package tech.solutionhut.ecommerce.order.mapper;

import org.springframework.stereotype.Service;
import tech.solutionhut.ecommerce.order.domain.Order;
import tech.solutionhut.ecommerce.order.domain.OrderLine;
import tech.solutionhut.ecommerce.order.record.OrderLineRequest;
import tech.solutionhut.ecommerce.order.record.OrderLineResponse;

@Service
public class OrderLineMapper {

    public OrderLine toOrderLine(OrderLineRequest orderLine) {
        return OrderLine.builder()
                        .id(orderLine.id())
                        .quantity(orderLine.quantity())
                        .order(Order.builder()
                                    .id(orderLine.orderId())
                                    .build())
                        .productId(orderLine.productId())
                        .build();
    }

    public OrderLineResponse toOrderLineResponse(OrderLine orderLine) {
        return new OrderLineResponse(
                orderLine.getId(),
                orderLine.getQuantity()
        );
    }
}

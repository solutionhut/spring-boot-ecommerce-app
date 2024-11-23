package tech.solutionhut.ecommerce.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.solutionhut.ecommerce.order.mapper.OrderLineMapper;
import tech.solutionhut.ecommerce.order.record.OrderLineRequest;
import tech.solutionhut.ecommerce.order.record.OrderLineResponse;
import tech.solutionhut.ecommerce.order.repository.OrderLineRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderLineService {

    private final OrderLineRepository repository;

    private final OrderLineMapper mapper;

    public Integer saveOrderLine(OrderLineRequest request) {
        var orderLine = mapper.toOrderLine(request);
        return repository.save(orderLine)
                         .getId();
    }

    public List<OrderLineResponse> findAllByOrderId(Integer orderId) {

        return repository.findAllByOrderId(orderId)
                         .stream()
                         .map(mapper::toOrderLineResponse)
                         .toList();
    }

    public void saveOrderLines(List<OrderLineRequest> orderLines) {
        var orderLinesToSave = orderLines.stream().map(mapper::toOrderLine).toList();
        repository.saveAll(orderLinesToSave);
    }
}

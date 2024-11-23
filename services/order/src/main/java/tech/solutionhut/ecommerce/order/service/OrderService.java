package tech.solutionhut.ecommerce.order.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.solutionhut.ecommerce.order.client.CustomerClient;
import tech.solutionhut.ecommerce.order.client.PaymentClient;
import tech.solutionhut.ecommerce.order.client.ProductClient;
import tech.solutionhut.ecommerce.order.exception.BusinessException;
import tech.solutionhut.ecommerce.order.kafka.OrderProducer;
import tech.solutionhut.ecommerce.order.mapper.OrderMapper;
import tech.solutionhut.ecommerce.order.record.*;
import tech.solutionhut.ecommerce.order.repository.OrderRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;

    private final ProductClient productClient;

    private final OrderRepository repository;

    private final OrderMapper mapper;

    private final OrderLineService orderLineService;

    private final OrderProducer orderProducer;

    private final PaymentClient paymentClient;


    public Integer createOrder(@Valid OrderRequest request) {
        // check for customer
        var customer = this.customerClient.findCustomerById(request.customerId())
                                          .orElseThrow(() -> new BusinessException("Cannot create order:: Customer not found for id: " + request.customerId()));

        // purchase the products --> product-ms
        var purchasedProducts = this.productClient.purchaseProducts(request.products());

        // persist order lines
        var order = this.repository.save(mapper.toOrder(request));

        var orderLines = request.products().stream().map(purchaseRequest -> new OrderLineRequest(null, order.getId(), purchaseRequest.productId(), purchaseRequest.quantity())).toList();

        orderLineService.saveOrderLines(orderLines);


        // start payment process
        var paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );

        paymentClient.requestOrderPayment(paymentRequest);

        // send the order confirmation --> notification-ms (kafka)
        orderProducer.sendOrderConfirmation(new OrderConfirmation(request.reference(), request.amount(), request.paymentMethod(), customer, purchasedProducts));
        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::fromOrder)
                         .toList();
    }

    public OrderResponse findById(Integer orderId) {
        return repository.findById(orderId)
                         .map(mapper::fromOrder)
                         .orElseThrow(() -> new EntityNotFoundException("Cannot find order:: Order not found for id: " + orderId));
    }
}

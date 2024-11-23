package tech.solutionhut.ecommerce.product.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.solutionhut.ecommerce.product.exception.ProductPurchaseException;
import tech.solutionhut.ecommerce.product.mapper.ProductMapper;
import tech.solutionhut.ecommerce.product.record.ProductPurchaseRequest;
import tech.solutionhut.ecommerce.product.record.ProductPurchaseResponse;
import tech.solutionhut.ecommerce.product.record.ProductRequest;
import tech.solutionhut.ecommerce.product.record.ProductResponse;
import tech.solutionhut.ecommerce.product.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    private final ProductMapper mapper;

    public Integer createProduct(@Valid ProductRequest request) {
        var product = mapper.toProduct(request);
        return repository.save(product)
                         .getId();
    }

    public List<ProductPurchaseResponse> purchaseProduct(@Valid List<ProductPurchaseRequest> requests) {
        var productIds = requests.stream()
                                 .map(ProductPurchaseRequest::productId)
                                 .toList();

        var storedProducts = repository.findAllByIdInOrderById(productIds);

        if (storedProducts.size() != productIds.size()) {
            throw new ProductPurchaseException("One or more products does not exists");
        }

        var requestedProductQuantityMap = requests.stream()
                                                  .collect(Collectors.toMap(ProductPurchaseRequest::productId, ProductPurchaseRequest::quantity));

        var purchasedProducts = new ArrayList<ProductPurchaseResponse>();

        for (var product : storedProducts) {
            if (product.getAvailableQuantity() < requestedProductQuantityMap.get(product.getId())) {
                throw new ProductPurchaseException("Insufficient stock quantity for product with id " + product.getId());
            }
            var productQuantity = requestedProductQuantityMap.get(product.getId());

            product.setAvailableQuantity(product.getAvailableQuantity() - productQuantity);
            repository.save(product);
            purchasedProducts.add(mapper.toProductPurchaseResponse(product, productQuantity));
        }

        return purchasedProducts;
    }

    public ProductResponse findById(Integer productId) {
        return repository.findById(productId)
                         .map(mapper::toProductResponse)
                         .orElseThrow(() -> new EntityNotFoundException("Product not found with the ID:: " + productId));
    }

    public List<ProductResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toProductResponse)
                         .collect(Collectors.toList());
    }
}

package tech.solutionhut.ecommerce.product.mapper;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import tech.solutionhut.ecommerce.product.domain.Category;
import tech.solutionhut.ecommerce.product.domain.Product;
import tech.solutionhut.ecommerce.product.record.ProductPurchaseResponse;
import tech.solutionhut.ecommerce.product.record.ProductRequest;
import tech.solutionhut.ecommerce.product.record.ProductResponse;

@Service
public class ProductMapper {


    public Product toProduct(@Valid ProductRequest request) {
        return Product.builder()
                      .id(request.id())
                      .name(request.name())
                      .description(request.description())
                      .price(request.price())
                      .availableQuantity(request.availableQuantity())
                      .category(Category.builder()
                                        .id(request.categoryId())
                                        .build())
                      .build();

    }

    public ProductResponse toProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getAvailableQuantity(),
                product.getPrice(),
                product.getCategory()
                       .getId(),
                product.getCategory()
                       .getName(),
                product.getCategory()
                       .getDescription()
        );
    }

    public ProductPurchaseResponse toProductPurchaseResponse(Product product, Double quantity) {
        return new ProductPurchaseResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                quantity
        );
    }
}

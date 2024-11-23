package tech.solutionhut.ecommerce.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.solutionhut.ecommerce.product.domain.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAllByIdInOrderById(List<Integer> productIds);
}

package tech.solutionhut.ecommerce.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.solutionhut.ecommerce.payment.domain.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}

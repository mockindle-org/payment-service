package com.mockindle.payment.repository;

import com.mockindle.payment.model.Payment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findAllByOrderByCreatedAtDesc();

    Optional<Payment> findFirstByOrderIdOrderByCreatedAtDesc(Long orderId);
}

package com.mockindle.payment.dto;

import com.mockindle.payment.model.Payment;
import java.time.OffsetDateTime;

/** Outbound representation of a payment. */
public record PaymentResponse(
        Long id,
        Long orderId,
        double amount,
        String status,
        OffsetDateTime createdAt) {

    public static PaymentResponse from(Payment payment) {
        return new PaymentResponse(
                payment.getId(),
                payment.getOrderId(),
                payment.getAmount(),
                payment.getStatus(),
                payment.getCreatedAt());
    }
}

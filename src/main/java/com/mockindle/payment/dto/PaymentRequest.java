package com.mockindle.payment.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/** Inbound body for POST /payments. */
public record PaymentRequest(
        @NotNull Long orderId,
        @Positive double amount) {
}

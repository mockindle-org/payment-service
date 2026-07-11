package com.mockindle.payment.dto;

import jakarta.validation.constraints.NotNull;

/** Inbound body for POST /refund — refunds the payment for a given order. */
public record RefundRequest(@NotNull Long orderId) {
}

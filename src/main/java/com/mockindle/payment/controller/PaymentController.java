package com.mockindle.payment.controller;

import com.mockindle.payment.dto.PaymentRequest;
import com.mockindle.payment.dto.PaymentResponse;
import com.mockindle.payment.dto.RefundRequest;
import com.mockindle.payment.service.PaymentService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @GetMapping("/payments")
    public List<PaymentResponse> list() {
        return service.list().stream().map(PaymentResponse::from).toList();
    }

    @PostMapping("/payments")
    public ResponseEntity<PaymentResponse> capture(@Valid @RequestBody PaymentRequest request) {
        PaymentResponse body = PaymentResponse.from(service.capture(request.orderId(), request.amount()));
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @PostMapping("/refund")
    public ResponseEntity<PaymentResponse> refund(@Valid @RequestBody RefundRequest request) {
        return service.refundByOrder(request.orderId())
                .map(PaymentResponse::from)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

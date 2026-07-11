package com.mockindle.payment.service;

import com.mockindle.payment.client.NotificationClient;
import com.mockindle.payment.model.Payment;
import com.mockindle.payment.repository.PaymentRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);

    private final PaymentRepository payments;
    private final NotificationClient notifications;

    public PaymentService(PaymentRepository payments, NotificationClient notifications) {
        this.payments = payments;
        this.notifications = notifications;
    }

    public List<Payment> list() {
        return payments.findAllByOrderByCreatedAtDesc();
    }

    /** Saves a captured payment, then asks notification-service to send a receipt. */
    public Payment capture(Long orderId, double amount) {
        Payment saved = payments.save(new Payment(orderId, amount, "CAPTURED"));
        try {
            notifications.sendEmail(
                    "order-" + orderId + "@example.com",
                    "Payment of " + amount + " for order " + orderId + " was received.");
        } catch (RuntimeException ex) {
            // Notification is best-effort; a delivery failure must not undo the payment.
            log.warn("notification-service call failed for payment on order {}: {}",
                    orderId, ex.getMessage());
        }
        return saved;
    }

    /** Refunds the most recent payment captured for the given order. */
    public Optional<Payment> refundByOrder(Long orderId) {
        return payments.findFirstByOrderIdOrderByCreatedAtDesc(orderId).map(payment -> {
            payment.setStatus("REFUNDED");
            return payments.save(payment);
        });
    }
}

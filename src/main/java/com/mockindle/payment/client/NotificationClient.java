package com.mockindle.payment.client;

import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

/**
 * Calls notification-service to send a receipt after a payment. Failure to
 * notify must not fail the payment, so callers treat errors as non-fatal.
 */
@Component
public class NotificationClient {

    private final RestClient restClient;
    private final String baseUrl;

    public NotificationClient(
            RestClient restClient,
            @Value("${services.notification.url}") String baseUrl) {
        this.restClient = restClient;
        this.baseUrl = baseUrl;
    }

    public void sendEmail(String recipient, String message) {
        restClient.post()
                .uri(baseUrl + "/email")
                .body(Map.of("recipient", recipient, "message", message))
                .retrieve()
                .toBodilessEntity();
    }
}

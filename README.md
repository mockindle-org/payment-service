# payment-service

Captures payments and issues refunds. On a successful capture it calls
**notification-service** to send a receipt.

- **Stack:** Java 17 + Spring Boot 3.3 (Spring Web, Spring Data JPA, Flyway)
- **Port:** 8083
- **Database:** Postgres on :5435 (`payments` table)
- **Downstream:** notification-service (`NOTIFICATION_SERVICE_URL`)

## Endpoints

| Method | Path | Description |
|---|---|---|
| `GET` | `/payments` | List payments, newest first |
| `POST` | `/payments` | Capture a payment, then notify (`{ "orderId": 1, "amount": 42.0 }`) |
| `POST` | `/refund` | Refund a payment (`{ "paymentId": 1 }`) |

## Run

```bash
docker compose up -d                 # Postgres on :5435
./mvnw spring-boot:run               # starts on :8083 (downloads Maven on first run)
```

The bundled Maven wrapper (`./mvnw`) downloads Maven automatically, so no local
Maven install is required — only a JDK 17+.

```bash
curl localhost:8083/payments -X POST -H 'content-type: application/json' \
  -d '{"orderId":1,"amount":42.0}'
```

## Configuration

| Env var | Default | Meaning |
|---|---|---|
| `DATABASE_URL` | `jdbc:postgresql://localhost:5435/payments` | JDBC URL |
| `DATABASE_USER` | `payment_service` | DB user |
| `DATABASE_PASSWORD` | `payment_service` | DB password |
| `NOTIFICATION_SERVICE_URL` | `http://localhost:8084` | notification-service base URL |

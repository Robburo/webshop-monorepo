package webshop.backend;

public class TodoList {
    // TODO: Security
    // - Store secrets (DB, JWT secret) in environment variables or Vault, not in code
    // - Add refresh token endpoint for longer sessions

    // TODO: Global Error Handling
    // - Add logging (SLF4J + Logback JSON logs)

    // TODO: DTO & Validation
    // - Add @Valid and javax/jakarta validation annotations (e.g., @NotNull, @Size, @Positive)
    // - Return meaningful error messages in GlobalExceptionHandler for validation failures

    // TODO: Service Layer
    // - Use transactional boundaries (@Transactional) on methods that update multiple entities
    // - Add idempotency to checkout (prevent double order on retry)
    // - Extract domain logic into smaller methods for clarity

    // TODO: Kafka Integration
    // - Add dependency: spring-kafka
    // - Configure Kafka producer & consumer in application.properties
    // - Create events:
    //   * ProductCreatedEvent, ProductUpdatedEvent, ProductDeletedEvent
    //   * OrderPlacedEvent, OrderStatusChangedEvent
    // - Create event publishers inside services (e.g., publish OrderPlacedEvent on checkout())
    // - Create consumers for analytics/microservices (e.g., InventoryService consumes OrderPlacedEvent)
    // - Store published events in DB for audit (outbox pattern)

    // TODO: Testing
    // - Add unit tests with JUnit5 + Mockito
    // - Add integration tests with @SpringBootTest and Testcontainers (Postgres + Kafka)
    // - Add API tests with RestAssured for major endpoints

    // TODO: Database & Persistence
    // - Add Flyway or Liquibase for DB migrations
    // - Use soft deletes where appropriate (e.g., orders)
    // - Index frequently queried columns (username, orderId, productId)

    // TODO: Documentation
    // - Swagger/OpenAPI annotations on all endpoints
    // - Group endpoints by domain (User, Product, Order, Cart, Admin)
    // - Generate OpenAPI spec and commit to repo

    // TODO: Performance & Caching
    // - Add caching for product/category read endpoints (Spring Cache + caffeine or Redis)
    // - Add pagination on all list endpoints (products, orders, cart items, etc.)

    // TODO: Observability
    // - Add structured logging with correlation IDs
    // - Add metrics with Micrometer + Prometheus
    // - Add tracing with OpenTelemetry

    // TODO: DevOps
    // - Add Dockerfile for backend
    // - Add docker-compose with backend, Postgres, Kafka, Zookeeper
    // - Prepare Kubernetes manifests for deployment
    // - Add CI/CD pipeline (GitHub Actions or GitLab CI) with build, test, and deploy stages
}

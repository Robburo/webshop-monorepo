package webshop.backend;

public class StuffToDo {
    // TODO Security and Auth

    /*Refresh tokens with expiration handling

    Role-based access control (ADMIN vs USER)

    Password reset and account activation via email*/

    // TODO Validation and Error Handling

    /*Use @Valid and Bean Validation for DTOs (price > 0, email format, etc.)

    Global exception handler with consistent error responses*/

    // TODO Order and Cart Enhancements

    /*Cart merge on login (guest → user)

    Order status workflow (PENDING → PAID → SHIPPED → DELIVERED)

    Payment integration stubs (Stripe, PayPal)*/

    // TODO Product Management

   /* Image upload (e.g. S3, local file system)

    Search and filtering with pagination

    Stock decrement on order*/

    // TODO User Features

    /*User profile endpoints

    Address management (billing/shipping)

    Wishlist / favorites table*/

    // TODO Infrastructure

    /*Swagger/OpenAPI annotations for all endpoints

    Logging with SLF4J + structured logs (JSON)

    Dockerfile and docker-compose (backend + Postgres)

    CI/CD setup (GitHub Actions or GitLab CI)*/

    // TODO Advanced (learning value)

   /* Caching with Redis (e.g. product catalog)

    Event-driven flow with Kafka (e.g. order placed → stock updated)

    Multi-tenant support for future scaling*/

    // TODO Kafka setup

    /*Dependencies (Maven)

    spring-kafka

    -- Configuration

    Kafka bootstrap servers in application.properties

    Producer/consumer configs

    Topic definitions (e.g. orders, products, users)

    -- Producer examples

    When an order is placed → publish OrderPlacedEvent

    When stock decreases → publish ProductStockUpdatedEvent

    When a user registers → publish UserRegisteredEvent

    -- Consumer examples

    Internal listener service that logs events for now

    Later, connect to other microservices or analytics pipeline

    -- Placement

    Each domain service publishes its own events (via a simple Kafka producer wrapper)

    Consumers live in a separate listener package to keep DDD separation*/
}

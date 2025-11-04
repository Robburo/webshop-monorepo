# Webshop Backend

Spring Boot REST API providing product, user, cart, and order management for the Webshop platform.

---

## ⚙️ Tech Stack

* Java 21
* Spring Boot 3
* Maven
* PostgreSQL + Flyway migrations
* Spring Security + JWT
* Swagger / OpenAPI for documentation
* Lombok for boilerplate reduction

---

## 🚀 Run Locally

```bash
cd backend
mvn spring-boot:run
```

Environment variables (default values shown):

```properties
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/webshop
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=postgres
JWT_SECRET=changeme
```

Backend runs at: **[http://localhost:8080](http://localhost:8080)**

---

## 🧩 API Modules

| Module   | Description                                     |
| -------- |-------------------------------------------------|
| product  | Product CRUD                                    |
| category | Category CRUD                                   |
| cart     | Add/remove/list cart items                      |
| order    | Checkout, order creation                        |
| user     | Authentication, registration, role-based access |
| admin    | Administrative endpoints                        |

---

## 🧪 Testing

```bash
mvn test
```

---

## 🧭 Swagger UI

```
http://localhost:8080/swagger-ui.html
```

---

## 🧰 Build JAR

```bash
mvn clean package
java -jar target/webshop-backend.jar
```

---

## 📦 Flyway Migrations

SQL scripts live under:

```
src/main/resources/db/migration/
```
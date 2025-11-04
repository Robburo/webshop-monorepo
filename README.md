# Webshop Monorepo

A full-stack Webshop platform built with **Spring Boot + PostgreSQL** on the backend and two interchangeable frontends:

* **React + Next.js + Tailwind**
* **Vue 3 + Nuxt + Tailwind**

Both frontends consume the same REST API, allowing experimentation with modern frontend stacks.

---

## 📁 Project Structure

```
webshop/
├── backend/           # Spring Boot backend (Java)
├── react-frontend/    # React + Next.js frontend
├── vue-frontend/      # Vue 3 + Nuxt frontend
└── infra/             # Docker & deployment setup (WIP)
```

---

## 💡 Tech Stack

| Layer            | Technology                                                                       |
| ---------------- | -------------------------------------------------------------------------------- |
| Backend          | Java 21, Spring Boot, Maven, PostgreSQL, Flyway, JWT Security, Swagger (OpenAPI) |
| Frontend (React) | React, Next.js, TailwindCSS							                          |
| Frontend (Vue)   | Vue 3, Nuxt, TailwindCSS						                                  |
| DevOps           | Docker, Docker Compose (planned)                                                 |

---

## 🚀 Quick Start

### 1. Clone the repo

```bash
git clone https://github.com/<your-username>/webshop.git
cd webshop
```

### 2. Run the backend

```bash
cd backend
mvn spring-boot:run
```

Backend runs on **[http://localhost:8080](http://localhost:8080)**

### 3. Run the frontend

Pick one:

**React (Next.js):**

```bash
cd react-frontend
npm install
npm run dev
```

🔗 [http://localhost:3000](http://localhost:3000)

**Vue (Nuxt):**

```bash
cd vue-frontend
npm install
npm run dev
```

🔗 [http://localhost:3000](http://localhost:3000)

---

## 🧭 API Documentation

Swagger UI available at:

```
http://localhost:8080/swagger-ui.html
```

---

## 🐳 Docker (optional)

To build containers (after Dockerfiles are ready):

```bash
docker-compose up --build
```
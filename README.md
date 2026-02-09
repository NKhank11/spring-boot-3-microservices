# Spring Boot 3 Microservices

E-commerce microservices application built with Spring Boot 3, demonstrating modern microservices architecture patterns.

## Architecture

### Microservices
- **Product Service** - Product catalog management (MongoDB)
- **Order Service** - Order processing and orchestration (MySQL)
- **Inventory Service** - Stock management (MySQL)
- **Notification Service** - Email notifications via Kafka (Mailtrap.io)
- **API Gateway** - Entry point with OAuth2 security (Spring Cloud Gateway MVC)
- **Frontend** - Angular SPA

### Tech Stack
- **Framework**: Spring Boot 3.5.10, Java 21
- **Security**: Keycloak OAuth2/OIDC
- **Communication**: 
  - Sync: RestClient HTTP Interface
  - Async: Kafka with Avro Schema Registry
- **Observability**: Prometheus, Grafana, Loki, Tempo
- **Resilience**: Resilience4j Circuit Breaker
- **API Docs**: SpringDoc OpenAPI (Swagger)
- **Deployment**: Docker, Kubernetes (Kind)

## Quick Start

### Prerequisites
- Java 21
- Docker & Docker Compose
- Maven
- kubectl (for Kubernetes deployment)

### 1. Run with Docker Compose

```bash
# Start infrastructure
docker-compose up -d

# Build and run services
./mvnw spring-boot:build-image -DdockerPassword=<your-dockerhub-token>

# Access applications
# Frontend: http://localhost:4200
# API Gateway: http://localhost:9000
# Keycloak: http://localhost:8181 (admin/admin)
# Grafana: http://localhost:3000
# Kafka UI: http://localhost:8086
```

### 2. Run on Kubernetes (Kind)

```bash
# Create Kind cluster
cd k8s/kind
./create-kind-cluster.sh

# Deploy infrastructure
kubectl apply -f k8s/manifests/infrastructure/

# Wait for infrastructure to be ready
kubectl wait --for=condition=ready pod -l app=mysql --timeout=300s

# Deploy applications
kubectl apply -f k8s/applications/

# Port forward to access services
kubectl port-forward svc/frontend 4200:80
kubectl port-forward svc/api-gateway 9000:9000
kubectl port-forward svc/keycloak 8181:8080
```

## Configuration

### Keycloak Setup
1. Access Keycloak at http://localhost:8181
2. Login with `admin/admin`
3. Realm `spring-microservices-security-realm` is auto-imported
4. Test users are preconfigured in the realm

### Notification Service
Configure Mailtrap.io credentials in `notification-service/application.properties`:
```properties
spring.mail.username=<your-mailtrap-username>
spring.mail.password=<your-mailtrap-password>
```

## API Documentation

Aggregated Swagger UI available at: http://localhost:9000/swagger-ui.html

## Monitoring

- **Grafana Dashboards**: http://localhost:3000
- **Prometheus Metrics**: http://localhost:9090
- **Distributed Tracing**: Tempo (via Grafana)
- **Logs**: Loki (via Grafana)

## Project Structure

```
├── api-gateway/              # API Gateway with OAuth2
├── product-service/          # Product management
├── order-service/            # Order processing
├── inventory-service/        # Inventory management
├── notification-service/     # Email notifications
├── frontend/                 # Angular frontend
├── k8s/                      # Kubernetes manifests
│   ├── applications/         # Service deployments
│   ├── manifests/            # Infrastructure
│   └── kind/                 # Kind cluster config
└── docker/                   # Docker configurations
```

## Key Features

- ✅ **Microservices Architecture** with domain-driven design
- ✅ **OAuth2 Security** with Keycloak
- ✅ **Event-Driven** with Kafka and Avro
- ✅ **Circuit Breaker** pattern for resilience
- ✅ **Distributed Tracing** with OpenTelemetry
- ✅ **Centralized Logging** with Loki
- ✅ **API Gateway** pattern with routing
- ✅ **Kubernetes Ready** with complete manifests
- ✅ **Integration Tests** with Testcontainers and WireMock

## Development

### Build Docker images
```bash
./mvnw spring-boot:build-image -DdockerPassword=<token>
```

### Run individual service
```bash
cd <service-name>
../mvnw spring-boot:run
```

### Database Migrations
- Flyway is configured for MySQL-based services
- Migrations run automatically on startup

## Author
**Nguyễn Nam Khánh** - @nkhank11

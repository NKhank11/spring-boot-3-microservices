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
- Node.js & npm (for frontend)
- kubectl & Kind (for Kubernetes deployment)

---

## 🔵 Option 1: Local Development (Docker Compose)

### Step 1: Build the Backend Services
```bash
# Clean and build all services (compile Java classes)
./mvnw clean install -DskipTests
```

### Step 2: Start Infrastructure with Docker Compose
```bash
# Start MySQL, MongoDB, Kafka, Keycloak, Prometheus, Grafana, etc.
docker-compose up -d
```

### Step 3: Run Backend Services
**Option A - Using IntelliJ IDEA:**
- Open the project in IntelliJ
- Go to Services panel and run all Spring Boot applications:
  - ApiGatewayApplication
  - ProductServiceApplication
  - OrderServiceApplication
  - InventoryServiceApplication
  - NotificationServiceApplication
  <img width="1071" height="346" alt="image" src="https://github.com/user-attachments/assets/78c1883a-3222-453d-a1bf-39d1f5bf01c7" />


**Option B - Using Terminal (run each in separate terminal):**
```bash
# API Gateway (port 9000)
cd api-gateway && ../mvnw spring-boot:run

# Product Service (port 8080)
cd product-service && ../mvnw spring-boot:run

# Order Service (port 8081)
cd order-service && ../mvnw spring-boot:run

# Inventory Service (port 8082)
cd inventory-service && ../mvnw spring-boot:run

# Notification Service (port 8083)
cd notification-service && ../mvnw spring-boot:run
```

### Step 4: Run Frontend
```bash
cd frontend
npm install
npm start
```

### 🌐 Access Applications (Local Docker Compose)
- **Frontend**: http://localhost:4200
- **API Gateway**: http://localhost:9000
- **Swagger UI**: http://localhost:9000/swagger-ui.html
- **Keycloak**: http://localhost:8181 (admin/admin) ⚠️ Port 8181 for local!
- **Grafana**: http://localhost:3000
- **Prometheus**: http://localhost:9090
- **Kafka UI**: http://localhost:8086
<img width="1916" height="501" alt="image" src="https://github.com/user-attachments/assets/86e41e8f-3847-4175-8122-3cf46a5058cd" />
<img width="1031" height="612" alt="image" src="https://github.com/user-attachments/assets/6e8ec79d-f8b0-4433-ba86-f4000e48a332" />
<img width="1919" height="926" alt="image" src="https://github.com/user-attachments/assets/a98ab5e9-f978-4415-ab92-7de9c748a123" />
<img width="1919" height="881" alt="image" src="https://github.com/user-attachments/assets/a03a2907-705e-4985-be9e-43300cc16ea3" />


---

## 🟢 Option 2: Kubernetes Deployment (Kind)

### Step 1: Build Docker Images
```bash
# Build and push images to Docker Hub
./mvnw spring-boot:build-image -DdockerPassword=<your-dockerhub-token>
```

### Step 2: Create Kind Cluster
```bash
cd k8s/kind
./create-kind-cluster.sh
```

### Step 3: Deploy Infrastructure
```bash
# Deploy MySQL, MongoDB, Kafka, Keycloak, etc.
kubectl apply -f k8s/manifests/infrastructure/

# Wait for infrastructure to be ready
kubectl wait --for=condition=ready pod -l app=mysql --timeout=300s
kubectl wait --for=condition=ready pod -l app=keycloak --timeout=300s
```

### Step 4: Deploy Microservices
```bash
kubectl apply -f k8s/applications/
```

### Step 5: Configure Local DNS (Windows)
Add this line to `C:\Windows\System32\drivers\etc\hosts`:
```
127.0.0.1 keycloak.default.svc.cluster.local
```

### Step 6: Port Forward Services
```bash
# Frontend
kubectl port-forward svc/frontend 4200:80

# API Gateway
kubectl port-forward svc/api-gateway 9000:9000

# Keycloak
kubectl port-forward svc/keycloak 8080:8080
```

### 🌐 Access Applications (Kubernetes)
- **Frontend**: http://localhost:4200
- **API Gateway**: http://localhost:9000
- **Swagger UI**: http://localhost:9000/swagger-ui.html
- **Keycloak**: http://localhost:8080 (admin/admin) ⚠️ Port 8080 for K8s!
  - Or: http://keycloak.default.svc.cluster.local:8080
- **Grafana**: http://localhost:3000 (after port-forward)

---

## 🔄 Key Differences Between Deployments

| Aspect | Local (Docker Compose) | Kubernetes (Kind) |
|--------|------------------------|-------------------|
| **Keycloak URL** | `localhost:8181` | `keycloak.default.svc.cluster.local:8080` or `localhost:8080` (port-forward) |
| **Backend Build** | `mvnw clean install` | `mvnw spring-boot:build-image` |
| **Backend Run** | IntelliJ or `mvnw spring-boot:run` | Kubernetes pods |
| **Frontend Build** | `npm start` (development) | Docker image (production) |
| **DNS Setup** | Not needed | Add `keycloak.default.svc.cluster.local` to hosts file |


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

### Build Project Locally (Without Docker)
```bash
# For running services via IntelliJ or mvnw spring-boot:run
./mvnw clean install -DskipTests
```

### Build Docker Images (For Kubernetes deployment)
```bash
# Build and push to Docker Hub
# Note: -DdockerPassword is ONLY required when building Docker images
./mvnw spring-boot:build-image -DdockerPassword=<your-dockerhub-token>
```

**📝 Note about `dockerPassword`:**
- **NOT needed** for local development with `docker-compose up`
- **ONLY needed** when building and pushing Docker images for Kubernetes
- The property was removed from `pom.xml` to avoid build errors during local compilation
- Pass it as a command-line argument when needed: `-DdockerPassword=xxx`

### Run Individual Service Locally
```bash
cd <service-name>
../mvnw spring-boot:run
```

### Run Frontend in Development Mode
```bash
cd frontend
npm install
npm start  # Uses localhost:8181 for Keycloak
```

### Build Frontend for Production (Kubernetes)
```bash
cd frontend
npm run build  # Uses keycloak.default.svc.cluster.local:8080
```

### Database Migrations
- Flyway is configured for MySQL-based services
- Migrations run automatically on startup

## Author
**Nguyễn Nam Khánh** - @nkhank11

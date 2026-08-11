# Parchemos Travel API

API REST en **Spring Boot 4** / **Java 21** para gestionar destinos, paquetes turísticos, usuarios, presupuestos, reservas, pagos y reseñas. Pensada para consumirse desde un frontend y desplegarse en **AWS ECS Express**.

## Stack

| Capa | Tecnología |
|------|------------|
| Lenguaje | Java 21 |
| Framework | Spring Boot 4.1.0 |
| Persistencia | Spring Data JPA / Hibernate |
| Base de datos | MySQL |
| Validación | Bean Validation |
| Auth | Email + BCrypt (sin JWT) |
| Docs | springdoc-openapi (Swagger UI) |
| Contenedor | Docker (Temurin 21) |
| CI/CD | GitHub Actions → ECR + ECS Express |

## Requisitos

- JDK 21+
- MySQL 8+ con base `parchemos_travel`
- Docker (opcional)

## Configuración

Copia `.env.example` a `.env` (no se sube a Git) o exporta las variables:

| Variable | Default | Descripción |
|----------|---------|-------------|
| `DB_URL` | `jdbc:mysql://localhost:3306/parchemos_travel?...` | JDBC URL |
| `DB_USERNAME` | `root` | Usuario MySQL |
| `DB_PASSWORD` | `123456` | Contraseña MySQL |
| `PORT` | `8080` | Puerto HTTP |

Hibernate usa `ddl-auto=update`: crea/actualiza tablas al arrancar. Hay seeders de admin, destinos y paquetes de Colombia.

## Cómo ejecutar

### Gradle (local)

```bash
./gradlew bootRun
```

En Windows:

```bash
gradlew.bat bootRun
```

### JAR

```bash
./gradlew bootJar
java -jar build/libs/*.jar
```

### Docker

```bash
docker build -t parchemos-travel .
docker run -p 8080:8080 \
  -e DB_URL="jdbc:mysql://host.docker.internal:3306/parchemos_travel?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC" \
  -e DB_USERNAME=root \
  -e DB_PASSWORD=123456 \
  parchemos-travel
```

## Documentación Swagger

Con la app en marcha:

| Recurso | URL |
|---------|-----|
| Swagger UI | http://localhost:8080/swagger-ui.html |
| OpenAPI JSON | http://localhost:8080/v3/api-docs |
| Health | http://localhost:8080/health |

## Endpoints principales

Paginación Spring: `page`, `size` (default 10, max 50), `sort`.

| Método | Ruta | Descripción |
|--------|------|-------------|
| `GET` | `/health` | Health check (ECS) |
| `POST` | `/api/auth/login` | Login |
| `POST` | `/api/auth/register` | Registro |
| `GET/POST` | `/api/usuarios` | Listar / crear usuarios |
| `GET/PUT/DELETE` | `/api/usuarios/{id}` | Usuario por ID |
| `GET/POST` | `/api/destinos` | Listar / crear destinos |
| `GET/PUT/DELETE` | `/api/destinos/{id}` | Destino por ID |
| `GET/POST` | `/api/paquetes` | Listar / crear paquetes |
| `GET` | `/api/paquetes/detalle` | Paquetes con detalle de destino |
| `GET` | `/api/paquetes/destino/{destinoId}` | Paquetes por destino |
| `GET/PUT/DELETE` | `/api/paquetes/{id}` | Paquete por ID |
| `GET/POST` | `/api/reservas` | Listar / crear reservas |
| `GET` | `/api/reservas/usuario/{usuarioId}` | Reservas por usuario |
| `GET` | `/api/reservas/paquete/{paqueteId}` | Reservas por paquete |
| `GET/PUT/DELETE` | `/api/reservas/{id}` | Reserva por ID |
| `GET/POST` | `/api/pagos` | Listar / crear pagos |
| `GET` | `/api/pagos/reserva/{reservaId}` | Pagos por reserva |
| `GET/PUT/DELETE` | `/api/pagos/{id}` | Pago por ID |
| `GET/POST` | `/api/presupuestos` | Listar / crear presupuestos |
| `GET` | `/api/presupuestos/usuario/{usuarioId}` | Presupuestos por usuario |
| `GET/PUT/DELETE` | `/api/presupuestos/{id}` | Presupuesto por ID |
| `GET/POST` | `/api/resenas` | Listar / crear reseñas |
| `GET` | `/api/resenas/paquete/{paqueteId}` | Reseñas por paquete |
| `GET/PUT/DELETE` | `/api/resenas/{id}` | Reseña por ID |

Errores centralizados (`404`, `400`, `409`, `401`, `500`) en formato `ErrorResponseDTO`.

## Dominio

```
Usuario ──┬── Presupuesto
          ├── Reserva ──── Pago
          └── Resena
Paquete ──┼── Destino
          ├── Reserva
          └── Resena
```

### Enums

- `RolUsuario`: `ADMIN`, `CLIENTE`
- `EstadoReserva`: `PENDIENTE`, `CONFIRMADA`, `CANCELADA`
- `EstadoPago`: `PENDIENTE`, `COMPLETADO`, `FALLIDO`
- `MetodoPago`: `TARJETA`, `TRANSFERENCIA`, `EFECTIVO`
- `TipoViajero`: `ECONOMICO`, `MODERADO`, `PREMIUM`

## Autenticación y seguridad

- Login/registro con email y contraseña hasheada (BCrypt).
- **No** hay JWT ni filtros de Spring Security: los endpoints están abiertos.
- CORS habilitado para `localhost:5500` y `https://jua039.github.io`.
- Admin sembrado (solo desarrollo): `gerencia.parchemos@admin.co` / `Parchemos#2026`.

## Estructura del proyecto

```
src/main/java/com/parchemos/travel/
├── config/          # CORS, BCrypt, OpenAPI, seeders
├── controller/      # REST
├── dto/             # Request/response + validación
├── exception/       # ApiExceptionHandler
├── model/           # Entidades JPA + enums
├── repository/      # Spring Data
└── service/         # Lógica de negocio
```

## Despliegue (ECS Express)

Push a `main` dispara `.github/workflows/deploy.yml`:

1. Build de imagen Docker
2. Push a Amazon ECR
3. Deploy del servicio Express en ECS
4. Health check en `/health`

Secretos de GitHub necesarios: `AWS_REGION`, `AWS_ACCESS_KEY_ID`, `AWS_SECRET_ACCESS_KEY`, `ECS_CLUSTER`, `ECS_SERVICE`, `ECR_REPOSITORY`, `CONTAINER_PORT`, `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`.

## Tests

```bash
./gradlew test
```

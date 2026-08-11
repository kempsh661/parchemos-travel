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



## Tests

```bash
./gradlew test
```

# HotelGuest — Backend · `room-service`

Microservicio REST reactivo que expone la API para la aplicación móvil **HotelGuest**, orientada a mejorar la experiencia de los huéspedes de un hotel.

---

## Tabla de Contenidos

- [Contexto de Negocio](#contexto-de-negocio)
- [Stack Tecnológico](#stack-tecnológico)
- [Arquitectura](#arquitectura)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Endpoints Disponibles](#endpoints-disponibles)
- [Configuración](#configuración)
- [Cómo Ejecutar](#cómo-ejecutar)
- [Health Check](#health-check)

---

## Contexto de Negocio

**HotelGuest** es una aplicación móvil que permite a los huéspedes de un hotel realizar pedidos del restaurante directamente a su habitación.  
Este repositorio es el backend que sirve los datos a dicha aplicación, implementado como un microservicio independiente (`room-service`) con programación reactiva (no bloqueante).

---

## Stack Tecnológico

| Tecnología | Versión | Uso |
|---|---|---|
| **Java** | 21 | Lenguaje principal |
| **Spring Boot** | 3.5.14 | Framework base |
| **Spring WebFlux** | — | API REST reactiva (no bloqueante) |
| **Spring Data R2DBC** | — | Acceso reactivo a la base de datos |
| **PostgreSQL** | 18.x | Base de datos relacional |
| **R2DBC PostgreSQL** | 1.0.6 | Driver reactivo para PostgreSQL |
| **Lombok** | 1.18.30 | Reducción de boilerplate |
| **MapStruct** | 1.5.5 | Mapeo entre capas |
| **SpringDoc OpenAPI** | 2.8.16 | Documentación de la API (Swagger UI) |
| **Gradle** | 8.x | Gestor de dependencias y build |

---

## Arquitectura

El proyecto sigue una **Arquitectura Hexagonal** (Ports & Adapters), que separa claramente la lógica de negocio de los detalles de infraestructura (base de datos, HTTP, etc.).

```
+------------------------------------------------------+
|                   CAPA DE ENTRADA                    |
|   RouterFunction (WebFlux) -> Handler -> UseCase     |
+------------------------------------------------------+
|                  CAPA DE DOMINIO                     |
|   Modelos de negocio · Puertos (interfaces) ·        |
|   Excepciones de dominio                             |
+------------------------------------------------------+
|               CAPA DE INFRAESTRUCTURA                |
|   RepositoryImpl (R2DBC) · Mappers · Row models      |
+------------------------------------------------------+
```

---

## Estructura del Proyecto

```
src/main/java/com/hotel/room_service/
│
├── RoomServiceApplication.java          # Punto de entrada
│
├── application/
│   └── ConsultarCategoriasUseCase.java  # Caso de uso: consulta y agrupa categorías
│
├── controller/
│   ├── MenuCategoriaRouter.java         # Definición de rutas (RouterFunction)
│   ├── MenuCategoriaHandler.java        # Manejador de peticiones HTTP
│   ├── HealthCheckController.java       # Endpoint de salud y conexión a BD
│   └── dto/
│       └── MenuCategoriaResponse.java   # DTO de respuesta al cliente
│
├── domain/
│   ├── model/
│   │   └── MenuCategoria.java           # Modelo de dominio
│   ├── port/
│   │   └── MenuCategoriaRepository.java # Puerto (interfaz) de acceso a datos
│   └── exception/
│       ├── AppException.java            # Excepción base de la aplicación
│       ├── BusinessException.java       # Errores de reglas de negocio
│       ├── NotFoundException.java       # Recurso no encontrado
│       ├── InternalServerErrorException.java
│       └── SqlLoadException.java        # Error al cargar archivos SQL
│
├── infrastructure/
│   ├── persistence/
│   │   └── MenuCategoriaRepositoryImpl.java  # Adaptador R2DBC (implementa el puerto)
│   ├── mapper/
│   │   └── MenuCategoriaRowMapper.java       # Mapea Row de R2DBC a MenuCategoriaRow
│   └── model/
│       └── MenuCategoriaRow.java             # Modelo de fila plana de la BD
│
└── shared/
    ├── ControllerAdvice.java            # Manejo global de excepciones
    ├── DatabaseConnectionValidator.java # Valida la conexión a BD al arrancar
    └── util/
        └── SqlLoaderUtil.java           # Carga archivos .sql desde el classpath

src/main/resources/
├── application.yaml                     # Configuración de la aplicación
├── schema.sql                           # DDL de la base de datos
└── querys/
    └── consultar-categoria.sql          # Consulta SQL del listado de categorías
```

---

## Endpoints Disponibles

### Menu — Categorías

| Método | Ruta | Descripción |
|--------|------|-------------|
| `GET` | `/api/v1/categorias` | Retorna la lista de categorías padre con sus subcategorías anidadas |

### Diagnóstico

| Método | Ruta | Descripción |
|--------|------|-------------|
| `GET` | `/health` | Verifica la conexión a la BD y el acceso a la tabla de categorías |

---

## Configuración

El archivo de configuración se encuentra en `src/main/resources/application.yaml`.

```yaml
spring:
  application:
    name: room-service

  r2dbc:
    url: r2dbc:postgresql://localhost:5432/postgres?sslMode=DISABLE&schema=public
    username: postgres
    password: <tu-contraseña>
    pool:
      initial-size: 5
      max-size: 20
      max-idle-time: 30m

server:
  port: 8081
```

### Variables a personalizar

| Variable | Descripción | Valor por defecto |
|---|---|---|
| `r2dbc.url` | URL de conexión R2DBC a PostgreSQL | `localhost:5432/postgres` |
| `r2dbc.username` | Usuario de la base de datos | `postgres` |
| `r2dbc.password` | Contraseña de la base de datos | — |
| `server.port` | Puerto en que escucha el servicio | `8081` |

---

## Cómo Ejecutar

### Prerrequisitos

- Java 21+
- PostgreSQL 18.x

### 1. Clonar el repositorio

```bash
git clone <url-del-repositorio>
cd HotelGuestBack
```

### 2. Configurar la base de datos

Editar `src/main/resources/application.yaml` y actualizar las credenciales de conexión.

### 3. Compilar y ejecutar

```bash
# Windows
.\gradlew.bat bootRun

# Linux / macOS
./gradlew bootRun
```

### 4. Ejecutar tests

```bash
.\gradlew.bat test      # Windows
./gradlew test          # Linux / macOS
```

---

## Health Check

Al arrancar, el servicio ejecuta automáticamente una validación de la conexión a la base de datos mediante el componente `DatabaseConnectionValidator`.

- **Si la conexión es exitosa:** se registra el mensaje `Conexión a la base de datos establecida correctamente.` en el log.
- **Si la conexión falla:** se registra un mensaje de error FATAL en el log con la causa exacta del fallo.

El estado puede consultarse en cualquier momento:

```
GET http://localhost:8081/health
```

---

## Manejo de Excepciones

El proyecto define una jerarquía de excepciones propia basada en `AppException`.  
Cada excepción se traduce automáticamente a su código HTTP correspondiente mediante el `ControllerAdvice` global.

### Jerarquía

```
AppException (abstracta)
├── InternalServerErrorException  →  HTTP 500  (errores de BD, red, SQL)
├── NotFoundException             →  HTTP 404  (recurso no encontrado)
├── BusinessException             →  HTTP 422  (reglas de negocio)
└── SqlLoadException              →  HTTP 500  (error al leer archivos .sql del classpath)
```

### Uso en la capa de infraestructura (R2DBC)

Capturar errores de base de datos y envolverlos en `InternalServerErrorException`:

```java
// MenuCategoriaRepositoryAdapter.java
@Override
public Mono<List<MenuCategoriaRow>> consultarCategorias() {
    return databaseClient.sql(sql)
            .map((row, metadata) -> menuCategoriaRowMapper.mapRow(row))
            .all()
            .collectList()
            .onErrorMap(R2dbcException.class, ex ->
                new InternalServerErrorException("Error al consultar categorías en BD", ex)
            );
}
```

### Uso en la capa de aplicación (casos de uso)

Lanzar `NotFoundException` cuando el resultado esté vacío:

```java
// ConsultarCategoriasUseCase.java
public Mono<List<MenuCategoria>> ejecutar() {
    return menuCategoriaRepository.consultarCategorias()
            .map(this::agruparCategorias)
            .flatMap(categorias -> {
                if (categorias.isEmpty()) {
                    return Mono.error(new NotFoundException("No se encontraron categorías"));
                }
                return Mono.just(categorias);
            });
}
```

### Uso con reglas de negocio

Lanzar `BusinessException` cuando se viola una regla de negocio antes de persistir:

```java
public Mono<Void> crearCategoria(MenuCategoria categoria) {
    if (categoria.getMecaNombre().isBlank()) {
        return Mono.error(new BusinessException("El nombre de la categoría es obligatorio"));
    }
    return menuCategoriaRepository.guardar(categoria);
}
```

### Uso con SqlLoadException

Se lanza automáticamente cuando `SqlLoaderUtil` no puede leer un archivo `.sql` del classpath. No requiere manejo manual; ocurre en la inicialización del adaptador:

```java
// Se lanza al arrancar si el archivo SQL no existe o no es legible
private final String sql = SqlLoaderUtil.load("querys/consultar-categoria.sql");
```

### Respuesta de error estándar

Todas las excepciones son capturadas por `ControllerAdvice` y devuelven la siguiente estructura JSON:

```json
{
  "message": "Descripción del error para el cliente"
}
```

| Excepción | HTTP | Cuándo usarla |
|---|---|---|
| `InternalServerErrorException` | 500 | Fallo en BD, red o SQL |
| `NotFoundException` | 404 | Recurso no encontrado |
| `BusinessException` | 422 | Validación de regla de negocio |
| `SqlLoadException` | 500 | Archivo `.sql` no encontrado en el classpath |

---

## Documentación de la API (Swagger UI)

Una vez la aplicación esté corriendo, los endpoints pueden explorarse desde:

```
http://localhost:8081/swagger-ui.html
```

---

> Proyecto en desarrollo activo — `v1.0.0`

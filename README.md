# Backend – Tienda de Tortas 🍰

Backend desarrollado como parte de la asignatura, utilizando **Spring Boot** y **Oracle** para gestionar una tienda de tortas con API REST segura y probada.

---

## 1. Objetivo del proyecto

Implementar la capa **backend** de una aplicación de tienda online que permita:

- Gestionar **productos** (tortas y otros).
- Gestionar **órdenes** y sus ítems.
- Autenticar usuarios mediante **JWT**.
- Exponer una **API REST** documentada y probada.

El foco de la asignatura está en: diseño de API, conexión a base de datos, seguridad básica y pruebas unitarias.

---

## 2. Tecnologías utilizadas

- **Java 17**
- **Spring Boot 4.0.0**
  - spring-boot-starter-webmvc
  - spring-boot-starter-data-jpa
  - spring-boot-starter-security
- **Oracle XE** (driver `ojdbc8`)
- **JPA / Hibernate**
- **JWT** (jjwt 0.11.5)
- **springdoc-openapi** (Swagger UI)
- **JUnit 5** + **Mockito** (pruebas unitarias)
- **Maven**

---

## 3. Estructura del proyecto (backend)

Principales paquetes:

- `com.tienda.tienda_backend.entity`  
  Entidades JPA: `Product`, `Order`, `OrderItem`, `User`, etc.
- `com.tienda.tienda_backend.repository`  
  Repositorios JPA (`ProductRepository`, `OrderRepository`, …).
- `com.tienda.tienda_backend.service`  
  Lógica de negocio (`ProductServiceImpl`, `OrderServiceImpl`, …).
- `com.tienda.tienda_backend.controller`  
  Controladores REST (`ProductController`, `OrderController`, …).
- `com.tienda.tienda_backend.dto`  
  DTOs como `ReduceStockRequest`.

---

## 4. Base de datos

- Motor: **Oracle XE**.
- Conexión configurada en `src/main/resources/application.properties`.
- Entidad principal del catálogo:

`Product`:
- `id` (Long, PK, auto-generado)
- `name` (String)
- `price` (Double)
- `description` (String)
- `stock` (int)

### 4.1. Carga inicial de datos

Se utiliza `data.sql` (`src/main/resources/data.sql`) para poblar la tabla `product` con tortas y otros productos al iniciar la aplicación, por ejemplo:

# Proyecto de Microservicio de Autenticación

Este proyecto es un **microservicio** desarrollado con **Spring Boot 3** que gestiona la autenticación y seguridad de usuarios utilizando **JWT (JSON Web Token)**. Contempla funcionalidades como **pre-registro, registro, inicio de sesión y recuperación de contraseña**, además del envío de correos electrónicos.

## Tecnologías Utilizadas

- **Spring Boot 3** - Framework principal para el desarrollo del microservicio.
- **Spring Security** - Implementación de autenticación y autorización mediante JWT.
- **Spring Data JPA** - Manejo de persistencia de datos con Hibernate.
- **ModelMapper** - Para la conversión entre DTOs y entidades.
- **JWT (JSON Web Token)** - Seguridad basada en tokens.
- **JavaMailSender** - Envío de correos electrónicos para confirmaciones y recuperación de contraseña.
- **PostgreSQL** - Base de datos (según configuración deseada).
- **Swagger** - Documentación interactiva de la API disponible en `http://localhost:8080/swagger-ui/index.html`.


## Instalación y Ejecución

### Requisitos previos
- **Docker** y **Docker Compose** deben estar instalados.
- Los puertos **8080** y **5432** deben estar libres.

### Pasos de instalación

1. **Clonar el repositorio**

2 **Ejecutar con Docker Compose**
   ```bash
   docker compose up -d
   ```

## Documentación
La API está documentada con **Swagger**, disponible en:
[Swagger UI](http://localhost:8080/swagger-ui/index.html)



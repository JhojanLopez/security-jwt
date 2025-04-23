# Proyecto de Microservicio de Seguridad

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

2. **Establecer variables de entorno**

A continuación se muestra un ejemplo de configuración usando Docker Compose: (revisar todas las variables en src/main/resources/application.yml)

```yaml
environment:
  SPRING_APPLICATION_HOST: http://localhost:8080
  SPRING_DATASOURCE_URL: jdbc:postgresql://security_db:5432/security_db
  SPRING_DATASOURCE_USERNAME: admin
  SPRING_DATASOURCE_PASSWORD: admin
  SPRING_APPLICATION_CLIENT_ACTIVE: true
  SPRING_APPLICATION_CLIENT_ENDPOINTS_REGISTRATION: /registration
  SPRING_APPLICATION_CLIENT_ENDPOINTS_RECOVERY_PASSWORD: /recovery-password
ports:
  - "8080:8080"
```

Por favor, consulte la siguiente tabla para más detalles de cada variable:

| Variable                                              | Valor por defecto                                   | Descripción                                             |
|-------------------------------------------------------|-----------------------------------------------------|---------------------------------------------------------|
| `SPRING_APPLICATION_HOST`                             | `http://localhost:8080`                             | URL base de la aplicación Spring.                       |
| `SPRING_DATASOURCE_URL`                               | `jdbc:postgresql://security_db:5432/security_db`    | Cadena de conexión JDBC para la base de datos de seguridad. |
| `SPRING_DATASOURCE_USERNAME`                          | `admin`                                             | Usuario para acceder a la base de datos.                |
| `SPRING_DATASOURCE_PASSWORD`                          | `admin`                                             | Contraseña para acceder a la base de datos.             |
| `SPRING_APPLICATION_CLIENT_ACTIVE`                    | `true`                                              | Indica si la funcionalidad de cliente está activa.      |
| `SPRING_APPLICATION_CLIENT_ENDPOINTS_REGISTRATION`    | `/registration`                                     | Ruta para el endpoint de registro de usuarios.          |
| `SPRING_APPLICATION_CLIENT_ENDPOINTS_RECOVERY_PASSWORD`| `/recovery-password`                                | Ruta para el endpoint de recuperación de contraseña de usuarios. |

3. **Ejecutar con Docker Compose**
   ```bash
   docker compose up -d
   ```

## Documentación
La API está documentada con **Swagger**, disponible en:
[Swagger UI](http://localhost:8080/swagger-ui/index.html)



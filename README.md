# Movii Tech Test - Backend API

Esta es la API de backend para la prueba técnica de Movii. Proporciona una base sólida con seguridad, documentación de API y persistencia de datos, integrando servicios externos como CleverTap.

## 🚀 Tecnologías

- **Java**: 17
- **Framework**: Spring Boot 3.2.2
- **Seguridad**: Spring Security + JWT (JSON Web Tokens)
- **Base de Datos**: MySQL
- **Persistencia**: Spring Data JPA
- **Documentación**: Springdoc OpenAPI / Swagger UI
- **Integración**: CleverTap API

## 🛡️ Seguridad

La aplicación implementa seguridad basada en tokens JWT.
- Las rutas bajo `/api/auth/**` son públicas para autenticación.
- El resto de las rutas requieren un token válido en el header `Authorization`.

## 📖 Documentación de la API (Swagger)

Una vez iniciada la aplicación, la documentación interactiva está disponible en:
`http://localhost:8080/swagger-ui/index.html`

Los docs en formato JSON se encuentran en:
`http://localhost:8080/v3/api-docs`

## ⚙️ Configuración

1. **Base de Datos**:
   Asegúrate de configurar las credenciales de MySQL en `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/movii_db
   spring.datasource.username=tu_usuario
   spring.datasource.password=tu_contraseña
   ```

2. **CleverTap**:
   Configura las credenciales de CleverTap para la integración:
   ```properties
   clevertap.account.id=TU_ID
   clevertap.account.token=TU_TOKEN
   ```

## 🏗️ Ejecución

Para ejecutar la aplicación localmente:

```bash
mvn spring-boot:run
```

O empaquetar y ejecutar:

```bash
mvn clean package
java -jar target/movii-test-0.0.1-SNAPSHOT.jar
```

---

Desarrollado con ❤️ para Movii.

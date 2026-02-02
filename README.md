# Movii Tech Test - Backend API

Esta es la API de backend para la prueba técnica de Movii. Proporciona una base sólida con seguridad, documentación de API y persistencia de datos, integrando servicios externos como CleverTap.

## 🚀 Tecnologías y Herramientas

- **Java**: 17 (Coretto/OpenJDK)
- **Framework**: Spring Boot 3.2.2
- **Seguridad**: Spring Security + JWT (JSON Web Tokens)
- **Base de Datos**: MySQL 8.0
- **Persistencia**: Spring Data JPA / Hibernate
- **Documentación**: Springdoc OpenAPI 2.3.0 (Swagger UI)
- **Integración**: CleverTap REST API Client
- **Mapeo**: MapStruct para conversión entre Entidades y DTOs

## 🏗️ Arquitectura del Sistema

La aplicación sigue un diseño basado en capas:
- **Controllers**: Manejo de peticiones HTTP y validación de entrada.
- **Services**: Lógica de negocio, validaciones transaccionales y procesos asíncronos.
- **Repositories**: Capa de abstracción de datos usando JPA.
- **Clients**: Integraciones con servicios externos (CleverTap).
- **Security**: Configuración de filtros JWT y protección de endpoints.

## 🛡️ Seguridad y Autenticación

El sistema implementa seguridad mediante **JWT**:
1. El usuario se autentica en `/auth/login` con credenciales válidas.
2. El sistema retorna un token JWT.
3. El frontend debe incluir este token en el header `Authorization: Bearer <token>` para acceder a rutas protegidas.

Rutas públicas: `/auth/**`, `/swagger-ui/**`, `/v3/api-docs/**`.

## 📖 Documentación de la API (Swagger)

La API está auto-documentada mediante Swagger. Puedes explorar los endpoints de forma interactiva en:
- **Swagger UI**: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- **API Docs (JSON)**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

## ⚙️ Configuración del Entorno

1. **Base de Datos**:
   Asegúrate de configurar las credenciales de MySQL en `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/movii_db
   spring.datasource.username=tu_usuario
   spring.datasource.password=tu_contraseña
   spring.jpa.hibernate.ddl-auto=update
   ```

2. **CleverTap Credentials**:
   ```properties
   clevertap.account.id=TU_ID
   clevertap.passcode=TU_PASSCODE
   clevertap.api.url=https://api.clevertap.com/1
   ```

## 🛠️ Ejecución Local

Clonar el repositorio y ejecutar mediante Maven:

```bash
mvn spring-boot:run
```

Para generar el archivo JAR ejecutable:
```bash
mvn clean package
java -jar target/movii-test-0.0.1-SNAPSHOT.jar
```

---

Desarrollado con ❤️ para Movii por Brando Rodriguez.

---

Desarrollado con ❤️ para Movii.

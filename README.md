# Carts Microservice

This microservice is in charge of making CRUD operations on the `Cart` domain
object.

## Libraries

The project uses the following stack:

| Runtime dependencies | Development libs     |
| -------------------- | -------------------- |
| Java 25 LTS          | Spring Boot DevTools |
| Maven v3.9.13        | SpringDoc            |
| Spring Boot v4.0.6   | Flyway Migration     |
| Lombok               | Spotless Maven       |
| Validation           | Palantir Java Format |
| Spring Data JPA      |                      |
| Driver Mysql         |                      |

## Development Setup & Execution

To set up and run the microservice in a development environment follow the next
steps:

1. Copy the `.env.example` into `.env`. Adjust if needed.
2. Start the **DB container** through Docker compose:

   ```bash
   docker compose up -d
   ```

   To check if the DB is working go to `http://localhost:8088` (or your `.env`
   setup) and use the provided credentials (`user` & `password` by default).

3. Start Spring Boot through the Maven wrapper:

   ```bash
   ./mvnw spring-boot:run
   ```

Done.

> [!TIP]
>
> You can read the API spec through the provided OpenAPI v3 interface at
> [http://localhost:8082/swagger-ui/index.html](http://localhost:8082/swagger-ui/index.html).
>
> Or import the OpenAPI specification into your preferred API client:
> [http://localhost:8082/v3/api-docs](http://localhost:8082/v3/api-docs)

---

**_Happy codding!_** ☕

# Hotel Application RestApi

RESTful web service built with Spring Boot 3 for hotel catalog management, amenity list editing, analytical histogram generation, and flexible multi-filter search.

---

## 🛠 Технологический стек

* **Java 21**
* **Spring Boot 3** (Spring Web, Spring Data JPA, Spring Validation)
* **H2 Database**
* **Liquibase** (database migration management)
* **JUnit 5 & Mockito** (unit testing)
* **Apache Maven** (project build tool)

---

## 🚀 API Endpoints

All endpoints are available with base path `/property-view`:

| HTTP Method | URL Path | Description | Returned status |
| :--- | :--- | :--- | :--- |
| `GET` | `/property-view/hotels` | Getting all hotels list | `200 OK` |
| `GET` | `/property-view/hotels/{id}` | Getting detailed info about hotel by ID | `200 OK` / `404 Not Found` |
| `POST` | `/property-view/hotels` | Creating new hotel | `201 Created` / `500 Internal Server Error` |
| `POST` | `/property-view/hotels/{id}/amenities` | Adding amenities to the existing hotel | `200 OK` / `404 Not Found` |
| `GET` | `/property-view/histogram/{param}` | Grouping hotels (`brand`, `city`, `country`, `amenities`) | `200 OK` / `400 Bad Request` |
| `GET` | `/property-view/search` | Searching hotels by params | `200 OK` |

---

## 🔗 Access Links & Documentation

When the application is running (default port: `8092`), the following web interfaces are accessible:

* **Swagger UI (Interactive API Documentation):**  
  [http://localhost:8092/swagger-ui.html](http://localhost:8092/swagger-ui.html)
* **OpenAPI Specification (JSON):**  
  [http://localhost:8092/v3/api-docs](http://localhost:8092/v3/api-docs)
* **H2 Database Console:**  
  [http://localhost:8092/h2-console](http://localhost:8092/h2-console)  
  *(Connection details: `JDBC URL: jdbc:h2:mem:hoteldb`, `User Name: root`, `Password: root`)*
  

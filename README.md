# Hotel Application RestApi

RESTful веб-сервис на базе **Spring Boot 3** для управления каталогом отелей, редактирования списка удобств (amenities), получения аналитической гистограммы и гибкого поиска по нескольким фильтрам.

---

## 🛠 Технологический стек

* **Java 21**
* **Spring Boot 3** (Spring Web, Spring Data JPA, Spring Validation)
* **H2**
* **Liquibase** (управление миграциями базы данных)
* **JUnit 5 & Mockito** (юнит тестирование)
* **Apache Maven** (сборка проекта)

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

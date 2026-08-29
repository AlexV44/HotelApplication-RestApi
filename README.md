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

## 🚀 API Эндпоинты

Все эндпоинты сервиса доступны по базовому пути `/property-view`:

| HTTP Метод | URL Path | Описание | Возвращаемый статус |
| :--- | :--- | :--- | :--- |
| `GET` | `/property-view/hotels` | Получение списка всех отелей | `200 OK` |
| `GET` | `/property-view/hotels/{id}` | Получение подробной информации об отеле по ID | `200 OK` / `404 Not Found` |
| `POST` | `/property-view/hotels` | Создание нового отеля | `201 Created` / `400 Bad Request` |
| `POST` | `/property-view/hotels/{id}/amenities` | Добавление удобств к существующему отелю | `200 OK` / `404 Not Found` |
| `GET` | `/property-view/histogram/{param}` | Группировка отелей (`brand`, `city`, `country`, `amenities`) | `200 OK` / `400 Bad Request` |
| `GET` | `/property-view/search` | Поиск отелей по параметрам | `200 OK` |

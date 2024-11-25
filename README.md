# News Website Backend

## 🛠️ Description
This is a **backend** project built with **Spring Boot** for a news website. It provides APIs to support functionalities such as:
- News management.
- News search.
- User registration and login.
- Role-based access management (user/admin).

---

## 🚀 Technologies Used
- **Java 17**: The primary programming language.
- **Spring Boot**: Main framework for the backend.
  - Spring Web
  - Spring Data JPA
  - Spring Security
  - JWT (JSON Web Token)
- **MongoDB**: NoSQL database.
- **Maven**: Dependency and build management tool.

---

## 📂 Project Structure
```plaintext
src/
├── main/
│   ├── java/com/sport/news/
│   │   ├── controller/   # REST API controllers
│   │   ├── service/      # Business logic services
│   │   ├── exception/    # Exception handling
│   │   ├── repository/   # Database interaction layers
│   │   ├── model/        # Data models (Entity, DTO)
│   │   ├── config/       # Configuration files (Security, JWT, etc.)
│   │   └── NewsApplication.java  # Application entry point
│   └── resources/
│       ├── application.properties # Main configuration file
│       └── static/          # Static resources
└── test/  # Unit and integration tests

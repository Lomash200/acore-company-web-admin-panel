# Acore Company Web Admin Panel – Backend

Backend service for the **Acore Company Web Admin Panel**, built using **Spring Boot**.
It provides REST APIs to manage website content, jobs, testimonials, user authentication,
file uploads, and email notifications.

---

## 🚀 Tech Stack
- Java
- Spring Boot
- Spring Security (JWT)
- MySQL
- JPA / Hibernate
- Maven
- Docker
- GitHub Actions

---

## ✨ Features
- Authentication (JWT-based)
- Blog Management (Admin & Public)
- News Management
- Project Management
- Job Openings & Job Applications
- Testimonials
- Contact / Enquiry Handling
- File & Image Upload
- Email Notifications (SMTP)
- Dockerized Deployment
- CI/CD with GitHub Actions

---

## 📂 Project Structure
acoreapi
├── src/main/java/com/acoreweb/acoreapi
│ ├── controller
│ ├── service
│ ├── repository
│ ├── model
│ ├── dto
│ ├── security
│ └── config
├── src/main/resources
│ └── application.properties
├── uploads
├── Dockerfile
└── pom.xml


---

## 🔐 Authentication
- JWT-based login
- Secured endpoints using Spring Security

---

## 🗄 Database
MySQL is used as the primary database.

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/acore_db
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD

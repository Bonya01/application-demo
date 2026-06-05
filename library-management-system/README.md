# Library Management System

A full-featured Library Management System built with Java 21, Spring Boot, Spring Data JPA, Hibernate, Spring Security (JWT), Thymeleaf, Bootstrap 5, and MySQL.

## Features
- Student Registration & Login
- Admin Login
- Add, Update, Delete Books
- Search Books
- Issue Books
- Return Books
- Fine Calculation
- Dashboard with Statistics
- User Profile Management

## Technologies
- Java 21
- Spring Boot
- Spring Data JPA
- Hibernate
- Spring Security with JWT
- Thymeleaf
- Bootstrap 5
- MySQL
- Maven

## Quick Start
1. Create a MySQL database named `library_db` and update `application.properties` with your DB credentials.
2. Build and run:

```bash
mvn clean package
mvn spring-boot:run
```

3. Visit http://localhost:8080

## Deploy
This project is now Docker-ready and can be deployed to any container-based host.

### Local Docker deployment
Build and run locally with Docker:

```bash
docker build -t library-management-system .
docker run -p 8080:8080 \
  -e JWT_SECRET=ChangeThisSecretForProd \
  -e SPRING_DATASOURCE_URL=jdbc:h2:mem:library_db;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE \
  library-management-system
```

Or use Docker Compose with MySQL:

```bash
docker compose up --build
```

### Continuous deployment via GitHub Actions
A workflow is included at `.github/workflows/docker-build.yml`.
- On push to `main`, it builds the JAR
- It builds the Docker image
- If `DOCKERHUB_USERNAME` and `DOCKERHUB_TOKEN` are set in GitHub Secrets, it pushes the image to Docker Hub

### Cloud deployment
1. Push the repository to GitHub.
2. Connect the repo to a Docker-friendly service such as Render, Railway, or any Kubernetes host.
3. Set these environment variables in your cloud service:
   - `SPRING_DATASOURCE_URL=jdbc:mysql://<host>:3306/library_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC`
   - `SPRING_DATASOURCE_USERNAME=<db-user>`
   - `SPRING_DATASOURCE_PASSWORD=<db-password>`
   - `JWT_SECRET=<secure-random-secret>`
   - `SERVER_PORT=8080`
   - `SPRING_JPA_HIBERNATE_DDL_AUTO=update`

### API Authentication
- Obtain a JWT token by POSTing to `/api/auth/login` with JSON `{ "username": "admin@example.com", "password": "admin123" }`.
- Use the returned token in `Authorization: Bearer <token>` for protected API calls.

## Notes
- The app uses H2 by default for local builds unless you set `SPRING_DATASOURCE_URL`.
- For production, use MySQL and a strong `JWT_SECRET`.

## Notes
- Replace the `jwt.secret` in `application.properties` with a secure value.

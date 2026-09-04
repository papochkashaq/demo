# JavaEE Demo — User Management Web App

A simple **Java EE (Jakarta EE)** MVC web application for managing users, backed by **PostgreSQL**. Built as an educational MVP demonstrating Servlets, JSP, JDBC and the DAO pattern without any additional frameworks (no Spring, no Hibernate).

Author: **Andrei Korolev**

## Table of Contents

- [JavaEE Demo — User Management Web App](#javaee-demo--user-management-web-app)
  - [Table of Contents](#table-of-contents)
  - [Features](#features)
  - [Tech Stack](#tech-stack)
  - [Prerequisites](#prerequisites)
  - [Project Structure](#project-structure)
  - [Getting Started](#getting-started)
    - [1. Clone the repository](#1-clone-the-repository)
    - [2. Configure the database connection](#2-configure-the-database-connection)
    - [3. Build the project](#3-build-the-project)
    - [4. Deploy to Tomcat](#4-deploy-to-tomcat)
    - [5. Run the application](#5-run-the-application)
  - [Usage](#usage)
  - [Database Schema](#database-schema)
  - [Known Limitations](#known-limitations)
  - [License](#license)

## Features

- List all registered users in a table ([`users.jsp`](src/main/webapp/WEB-INF/views/users/users.jsp:1))
- Create a new user via an HTML form ([`create.jsp`](src/main/webapp/WEB-INF/views/users/create.jsp:1))
- Uniqueness check for the email field before insertion
- Automatic `createdAt` / `updatedAt` timestamps
- PostgreSQL persistence via plain JDBC (no ORM)
- Classic MVC layering: Servlet (controller) → JSP (view) → DAO (data access)

## Tech Stack

| Layer            | Technology                          |
|-------------------|--------------------------------------|
| Language          | Java 25                              |
| Web layer         | Jakarta Servlet 6.1, JSP, JSTL       |
| Persistence       | JDBC, PostgreSQL 42.7.x driver       |
| Build tool        | Maven (packaged as `.war`)           |
| Application server| Apache Tomcat (Jakarta EE 6.0 compatible) |
| Testing           | JUnit Jupiter 5.13                   |

## Prerequisites

Make sure the following are installed before you start:

- [JDK 25](https://www.oracle.com/java/technologies/downloads/) (or a compatible OpenJDK build)
- [Apache Maven](https://maven.apache.org/download.cgi)
- [PostgreSQL](https://www.postgresql.org/download/)
- [Apache Tomcat](https://tomcat.apache.org/download-11.cgi) (a version compatible with Jakarta EE 6.0 / Servlet 6.1, e.g. Tomcat 11)

## Project Structure

```
demo
├── pom.xml
├── README.md
├── Original Technical Specifications.md
└── src
    └── main
        ├── java/com/alderson/demo
        │   ├── UsersApp.java                # DB connection & schema bootstrap
        │   ├── controller/UsersController.java   # Servlet handling /users
        │   └── service
        │       ├── UserDAO.java             # Data access layer (JDBC)
        │       ├── UserDTO.java             # User data transfer object
        │       └── UserService.java         # Business/service layer
        ├── resources
        │   ├── db.properties                # DB credentials (create this file, see below)
        │   ├── schema.sql                    # Table creation script
        │   └── style.css
        └── webapp/WEB-INF
            ├── web.xml                       # Servlet mappings
            └── views
                ├── index.jsp
                └── users
                    ├── create.jsp
                    ├── users.jsp
                    └── email-error.jsp
```

## Getting Started

### 1. Clone the repository

```bash
git clone <repository-url>
cd demo
```

### 2. Configure the database connection

Create a PostgreSQL database for the application, then create the file `src/main/resources/db.properties` with your credentials:

```properties
db.url=jdbc:postgresql://localhost:5432/<your_database>
db.username=<your_username>
db.password=<your_password>
```

> The `users` table is created automatically on first request to the application's main page (`/`), using the script in [`schema.sql`](src/main/resources/schema.sql:1) — no manual migration step is required.

### 3. Build the project

From the project root, run:

```bash
mvn clean package
```

This produces a `.war` file inside the `target` folder.

### 4. Deploy to Tomcat

Copy the generated `.war` file from `target/` into `<CATALINA_HOME>/webapps/`.

### 5. Run the application

Start the Tomcat server:

- **Windows:**
  ```bat
  %CATALINA_HOME%\bin\startup.bat
  ```
- **Linux / macOS:**
  ```bash
  $CATALINA_HOME/bin/startup.sh
  ```

The application will be available at:

```
http://localhost:8080/<war-name>/
```

## Usage

| Action              | URL              | Description                                   |
|---------------------|------------------|------------------------------------------------|
| Home page           | `/`              | Landing page with navigation links, also triggers DB/schema initialization |
| List users          | `/users` (GET)   | Displays all users in a table                  |
| Create user (form)  | `/users/create`  | Shows the "create user" form                   |
| Create user (submit)| `/users` (POST)  | Creates a new user; redirects to `/users` on success, or `/users/email-error` if the email is already taken |

## Database Schema

Defined in [`schema.sql`](src/main/resources/schema.sql:1):

```sql
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(128),
    email VARCHAR(128),
    dateOfBirth DATE,
    createdAt TIMESTAMP,
    updatedAt TIMESTAMP
);
```

## Known Limitations

This project is an educational MVP and intentionally omits production-grade concerns:

- No authentication or authorization.
- No REST API — only server-rendered JSP views.
- Minimal input validation: [`UsersController.addUser()`](src/main/java/com/alderson/demo/controller/UsersController.java:57) does not validate `name`, `email` or `dateOfBirth` for null/empty values, and an invalid date format will raise an unhandled `DateTimeParseException` in [`UserDTO`](src/main/java/com/alderson/demo/service/UserDTO.java:20).
- No connection pooling — a new JDBC connection is opened per request via [`UsersApp.getConnection()`](src/main/java/com/alderson/demo/UsersApp.java:21).
- Database schema initialization is triggered from the JSP layer ([`index.jsp`](src/main/webapp/WEB-INF/views/index.jsp:7)) instead of a proper `ServletContextListener`.
- No edit/delete functionality, pagination, or unit tests for the DAO layer.

See [`Original Technical Specifications.md`](Original%20Technical%20Specifications.md:1) for the full original requirements and stretch goals.

## License

No license has been specified for this project. All rights reserved by the author unless stated otherwise.

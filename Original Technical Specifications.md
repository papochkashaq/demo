ТЗ

## 🧩 Task: Simple User Management Web Application (MVP)

### Goal

Build a simple Java web application that displays a list of users stored in a PostgreSQL database.
This is an MVP: no authentication, no authorization, focus on fundamentals.

---

## 🛠 Tech Stack (Required)

* Java
* Java Servlets
* JSP (for frontend rendering)
* PostgreSQL
* JDBC (or a simple connection pool)
* Maven or Gradle (project setup)
* Apache Tomcat (or similar servlet container)

---

## 📌 Functional Requirements

### 1. User Entity

Each user must have the following fields:

* id (UUID or auto-generated)
* name (String, required)
* email (String, required, unique)
* dateOfBirth (Date)
* createdAt (Timestamp)
* updatedAt (Timestamp)

---

### 2. Database

* Use PostgreSQL as the data store
* Create a users table with appropriate data types
* createdAt and updatedAt should be set automatically
* Include SQL migration or schema script

---

### 3. Web Application Features

#### 3.1 List Users

* Endpoint: /users
* Display all users in a table using JSP
* Show:

    * Name
    * Email
    * Date of Birth
    * Created At
    * Updated At

#### 3.2 Create User

* Endpoint: /users/create
* JSP form for creating a new user
* Basic validation:

    * Name is required
    * Email is required and valid format
* On success, redirect back to the user list

---

## 🧱 Architecture Expectations

* Use MVC-style separation

    * Servlet → Controller
    * JSP → View
    * DAO / Repository → Database access
* No business logic inside JSPs
* SQL should not be written directly in Servlets

Example structure:

com.example.app
├── servlet
├── dao
├── model
├── util
└── resources
---

## ✅ Acceptance Criteria

* Application starts without errors
* Users can be added via UI
* Users are persisted in PostgreSQL
* /users page displays all users correctly
* JSP pages render without Java logic abuse (`<% %>` minimized)
* Code is readable and logically structured

---

## 🚫 Out of Scope (for this task)

* Authentication / Authorization
* REST APIs
* Frameworks like Spring / Hibernate
* Styling beyond basic HTML (optional CSS is fine)

---

## 🌱 Bonus (Optional / Stretch Goals)

If time allows:

* Edit user
* Delete user
* Server-side validation errors shown in JSP
* Pagination for user list
* Use PreparedStatement everywhere
* Add simple unit test for DAO layer

---

## 📝 Deliverables

* Git repository link
* SQL schema or migration file
* README with:

    * How to run the app
    * DB setup instructions
    * Assumptions made
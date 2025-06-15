# 🛒 E-Commerce Backend Application

This is a **Spring Boot-based E-Commerce Backend Application** designed with modular architecture. It supports features like user authentication, product management, cart, orders, and admin operations.

## ✅ Features

### 1. **Authentication & User Profile Management**
- User registration and login
- Password encryption with Spring Security
- JWT token-based authentication
- Role-based access control (User/Admin)
- Profile update & retrieval

### 2. **Product Management (Admin Only)**
- Add new products
- Update existing products
- Delete products
- View all products (publicly accessible)

### 3. **Cart Management**
- Add items to cart
- Remove items from cart
- View cart items
- Update cart quantity

### 4. **Order Management**
- Place order from cart
- View order history
- Cancel order

### 5. **Admin Dashboard Module**
- View all users
- Manage products
- View total orders & revenue
- Dashboard analytics (data-driven insights)

---

## 🛠️ Tech Stack

| Layer         | Technology           |
|---------------|----------------------|
| Backend       | Spring Boot          |
| REST API      | Spring Web (REST)    |
| Security      | Spring Security, JWT |
| Persistence   | Spring Data JPA      |
| Database      | MySQL                |
| Testing Tool  | Postman              |
| Build Tool    | Maven                |

---

## 🔐 Authentication Flow (JWT)

- Upon login, a **JWT Token** is generated.
- This token is sent in the `Authorization` header as `Bearer <token>` for all protected routes.
- Token validation is handled by a custom filter integrated into Spring Security.

---

## 🧪 API Testing

All endpoints have been thoroughly tested using **Postman**.
> A sample Postman collection is provided in the `postman/` folder (if available).

---

## ⚙️ Setup Instruction
### 1. Clone the repository

```bash
https://github.com/Thanushs25/ecommerce-spring-boot-backend.git
cd ecommerce-spring-boot-backend
cd EvoCommerce - Monolythic
```

### 2. Configure the application
- Update the application.yaml with your own DB credentials:
    ```yaml
    spring:
    datasource:
      url: jdbc:mysql://localhost:3306/your_database_name
      username: root
      password: your_password
      driver-class-name: com.mysql.cj.jdbc.Driver
    jpa:
      hibernate:
        ddl-auto: create
      show-sql: true
      properties:
        hibernate:
          format-sql: true
      database: mysql
      database-platform: org.hibernate.dialect.MySQL8Dialect
    ```
    
### 3. Create MySQL database
  ```sql
  `  CREATE DATABASE your_database_name
  ```

### 4. Build and run the project
  ```bash
  ./mvnw clean install
  ./mvnw spring-boot:run
  ```
  The server will run at: http://localhost:8080

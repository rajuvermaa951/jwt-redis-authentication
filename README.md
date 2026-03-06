🔐 JWT Authentication with Redis Token Blacklisting
📌 Overview

This project demonstrates stateless authentication using JWT (JSON Web Tokens) with Spring Boot and Spring Security, enhanced with Redis-based token blacklisting to support secure logout functionality.

Normally, JWT is stateless and cannot be invalidated once issued. This project solves that limitation by storing logged-out tokens in Redis, preventing further use until the token expires.

🚀 Features

User authentication using Spring Security

JWT-based stateless authentication

OncePerRequestFilter for request-level authentication

Redis integration for token blacklisting

Secure logout support

Password encryption using BCrypt

Layered architecture (Controller → Service → Repository)

🧠 Architecture
Client
   │
   │ Login Request
   ▼
AuthController (/auth/login)
   │
   ▼
AuthenticationManager
   │
   ▼
JWT Generated
   │
   ▼
Client Stores Token
   │
   │ Authorization: Bearer <token>
   ▼
AuthenticationFilter (OncePerRequestFilter)
   │
   ├── Check Redis Blacklist
   │
   └── Validate JWT
          │
          ▼
   SecurityContext Updated
          │
          ▼
      Protected APIs
🗂 Project Structure
com.jwt.redis
│
├── config
│   └── SecurityConfig
│   └── RedisConfig
├── controller
│   └── AuthController
│   └── RedisController
│   └── StudentController
│   └── TestController
├── dto
│   ├── LoginRequest
│   └── AuthResponse
│
├── entity
│   └── User
│   └── Student
│   
├── repository
│   └── UserRepository
│   └── StudentRepository
│
├── security
│   ├── JwtUtil
│   └── AuthenticationFilter
│   └── JwtProperties
│   └── CustomUserDetails
│
├── service
│   ├── CustomUserDetailsService
│   └── TokenBlackListService
│   └── RedisService
│   └── StudentService
│   └── StudentCacheService

🔑 Authentication Flow
1️⃣ Login
POST /auth/login

Request body:

{
  "username": "raju",
  "password": "1234"
}

Response:

{
  "token": "JWT_TOKEN_HERE"
}
2️⃣ Access Protected API
GET /api/test
Authorization: Bearer <JWT_TOKEN>

Flow:

JWT extracted from request header

Redis checked for blacklist

Token validated

SecurityContext populated

Request processed

3️⃣ Logout
POST /auth/logout
Authorization: Bearer <JWT_TOKEN>

Process:

Token extracted from request

Stored in Redis blacklist

Future requests with this token are rejected

🗄 Redis Usage

Redis is used to store blacklisted JWT tokens.

Example Redis entry:

JWT_TOKEN → blacklisted

When a request arrives:

Check Redis
   │
   ├─ Token exists → Reject request
   └─ Token not found → Continue authentication
⚙️ Tech Stack

Java 17+

Spring Boot

Spring Security

JWT (JJWT)

Redis

Maven

MySQL  (for user data)

🔒 Security Components
Component	Purpose
AuthenticationFilter	Intercepts requests and validates JWT
JwtUtil	Generates and validates JWT tokens
CustomUserDetailsService	Loads users from database
TokenBlackListService	Stores invalidated tokens in Redis
SecurityConfig	Configures Spring Security
🧪 Testing with Postman
Login
POST /auth/login

Copy the returned JWT token.

Access API
Authorization: Bearer <token>
Logout
POST /auth/logout
Authorization: Bearer <token>

After logout, the same token will return 401 Unauthorized.

🔄 Future Improvements

Refresh Token mechanism

API rate limiting using Redis

Role-based authorization

API documentation with Swagger

👨‍💻 Author

Raju Verma

Backend Developer | Java | Spring Boot | Spring Security | Redis

⭐ Key Learning Outcomes

This project demonstrates:

Stateless authentication with JWT

Implementing logout in JWT systems

Using Redis for security mechanisms

Understanding Spring Security filter chains

Building scalable backend authentication systems
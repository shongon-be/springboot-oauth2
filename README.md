# OAuth2 Demo Project

This is a basic application that demonstrates how to implement **OAuth2 authentication** using both **Google** and **GitHub** as identity providers. The project is built with a **React frontend** and a **Spring Boot backend**, designed to show how modern applications can securely authenticate users using external providers.

The frontend allows users to initiate login with a single click, while the backend handles the authentication flow, token validation, and user information extraction. After successful login, basic user profile data (e.g. name, email, avatar) is retrieved and displayed on the UI.

The project follows a **separated frontend-backend architecture** with communication via RESTful APIs. It also integrates **Swagger UI** for backend API documentation and leverages **environment variables (`.env`)** to protect sensitive OAuth credentials such as client ID and client secret. This makes the project more secure and production-ready by ensuring secrets are not exposed in source code.

---

## Table of Contents

- [Getting Started](#getting-started)
- [Project Structure](#project-structure)
- [Features](#features)
- [Requirements](#requirements)
- [Configuration](#configuration)
- [Usage](#usage)
- [API Documentation](#api-documentation)
- [Security](#security)

## Getting Started

This project is divided into two parts: a **React frontend** and a **Spring Boot backend**. Follow the steps below to set up each part.

### Front-end Setup

- Built with **Create React App**.
  
- Navigate to the `frontend/` directory.
  
- Run the following commands:

```bash
cd /frontend
npm install      # Install dependencies
npm start        # Start development server on http://localhost:3000
```

### Back-end Setup
- Built with Spring Boot.

- Navigate to the `backend/` directory.

- Run the following commands:

```bash
cd /backend
mvn clean install       # Build the backend project
mvn spring-boot:run     # Start server on http://localhost:8080
```

## Project Structure
```
root/
│
├── frontend/         # ReactJS application (UI)
│   ├── public/
│   └── src/
│       ├── components/
│       └── App.js
│
└── backend/          # Spring Boot application (API & Security)
    ├── src/
    │   ├── main/
    │   │   ├── java/
    │   │   │   └── com/example/oauth2/
    │   │   └── resources/
    │   └── test/
    ├── .env # OAuth client ID/secret (loaded manually or via config server)
    └── pom.xml
```
## Features
- ✅ OAuth2 login with Google and GitHub.

- ✅ Display authenticated user information.

- ✅ Secure RESTful APIs with Spring Security.

- ✅ API documentation with Swagger UI.

- ✅ Modular project structure separating frontend and backend.

## Requirements
- Node.js (for React frontend).

- Java 11 (for Spring Boot backend).
  
- Maven (for building and running the backend).

## Configuration
- This project uses `.env` files to store sensitive configuration such as OAuth2 client credentials and API URLs. These files are **not committed to the repository** and must be created manually before running the application.

- Create `.env` to store variables then put them into `application.yml` under `backend/src/main/resources` to keep it's secret.

    (⚠️Remember to add it to `.gitignore`)

- Create a `.env` file:

```.env
GOOGLE_CLIENT_ID=your_google_client_id
GOOGLE_CLIENT_SECRET=your_google_client_secret
GITHUB_CLIENT_ID=your_github_client_id
GITHUB_CLIENT_SECRET=your_github_client_secret
```

- Access to https://console.cloud.google.com/ to setup and create Google Oauth2 credentials.

- In GitHub, "Settings -> Developer Settings -> OAuth Apps" to setup and create GitHub Oauth2 credentials.

- Then you can bind them via `application.yml` like:

```yaml
spring:
    security:
        oauth2:
            client:
                registration:
                    github:
                        client-id: ${GITHUB_CLIENT_ID}
                        client-secret: ${GITHUB_CLIENT_SECRET}
                        scope: user:email,read:user
                    google:
                        client-id: ${GOOGLE_CLIENT_ID}
                        client-secret: ${GOOGLE_CLIENT_SECRET}
```

## Usage
1. Open the browser at http://localhost:3000

2. Click on either:
    - Login with Google.
    - Login with GitHub.

3. After successful authentication:
    - The user will be redirected back to the frontend.
    - Basic user information (name, email, avatar) will be displayed.

## API Documentation
- Backend REST API documentation is available at: http://localhost:8080/swagger-ui.html

- Explore available REST endpoints, including those related to authentication and user profile.

## Security
- OAuth2 is implemented using Spring Security OAuth2 Client.

- The backend is secured using Spring Security.

- CORS and CSRF are configured for frontend-backend communication.

- After login, access tokens are used to authenticate requests between frontend and backend.

## Resources
[Complete OAuth2 Course with Spring Boot & React | GitHub & Google Integration Step By Step Tutorial - EmbarkX](https://www.youtube.com/watch?v=JUOSHhbbBOY)

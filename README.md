# LoreKeeper API

![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.x-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16+-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Java 17](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)

> A personalized, self-hosted backend for cataloging books, mangas, and comics, featuring Open Library API integration.

---

## Overview

LoreKeeper is a robust backend REST API built to serve as a personal reading tracker. Whether tracking epic fantasy novels or weekly manga chapters, LoreKeeper acts as the single source of truth for a digital library.

Unlike generic trackers, this API is designed with strict architectural standards, featuring a clean 4-layer design (Entity -> DTO -> Repository -> Controller) and centralized error handling.

## Features

- **Centralized Cataloging**: Track books, comics, and manga in one unified database.
- **Open Library Integration**: Automatically fetch book metadata, page counts, and cover art using the Open Library API.
- **User Tracking**: Keep tabs on reading status (`READING`, `COMPLETED`, `ON_HOLD`), current page/chapter, and personal ratings.
- **Secure Authentication**: Stateless JWT-based security featuring traditional Email/Password login and Google OAuth integration.
- **Strict Validation**: Bulletproof data integrity using Jakarta Validation and a centralized `GlobalExceptionHandler`.
- **Workflow Driven**: Developed with rigorous AI agent workflows enforcing clean git histories and architectural consistency.

## Tech Stack

- **Framework:** Spring Boot (Web, Data JPA, Validation)
- **Language:** Java 17
- **Security:** Spring Security & JWT (`io.jsonwebtoken`)
- **Database:** PostgreSQL
- **Build Tool:** Maven
- **External Integrations:** Open Library API (via `RestTemplate`), Google OAuth (Sign-in)

## Getting Started

### Prerequisites
- Java 17 or higher
- PostgreSQL
- Maven

### Local Setup

1. **Clone the repository:**
   ```bash
   git clone https://github.com/TheNirajMahale/lorekeeper-api.git
   cd lorekeeper-api
   ```

2. **Configure the Database:**
   Ensure you have a PostgreSQL instance running. Update the `src/main/resources/application.properties` with your credentials if they differ from the defaults:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5433/mydb
   spring.datasource.username=niraj
   spring.datasource.password=root
   ```

3. **Run the Application:**
   ```bash
   ./mvnw spring-boot:run
   ```
   The API will be available at `http://localhost:8080`.

## Documentation & Architecture

For a deep dive into the exact database schema, API contracts, and the Open Library 3-step lookup flow, please read the core specification document:
[**LoreKeeper.md**](./LoreKeeper.md)

## Development Standards & AI Review

This repository enforces strict coding standards and Git workflows. AI tools are utilized strictly for code-review and enforcing architectural consistency. If you are an AI assistant analyzing this repo, you **must** read and adhere to the rules defined in the [`.agents/`](./.agents) directory.

- **Architecture:** 4-layer pattern only.
- **Git:** Conventional commits (`type(scope): subject`). No direct commits to `main`.
- **Testing:** Mandatory `curl` and manual testing before any code is committed.

---
*Maintained by [Niraj](https://github.com/TheNirajMahale).*

# Deep dive in Spring Security

## Project Description

This project aims to explore Spring Security fundamentals, covering topics such as authentication, authorization, Spring Security Filter Chain, Basic Auth (might not be in repo at the end since I'll use JWT), JWT tokens, possibly touch on OAuth and using JDBC API since I have not used it before.

## Project Goals

- Learn Spring Security fundamentals
- Authentication & Authorization
- Spring Security Filter Chain
- Basic Auth (might not be in repo at the end since I'll use JWT)
- JWT tokens
- Maybe touch on OAuth, we will see.
- For the first time try out JDBC API instead of JPA and learn DAO pattern, etc.

## Datasource and Domain

- Local Postgres DB and Adminer as the DB manager
- User and Movie entities with a Many-to-Many relationship

## Installation

### DB
- Clone the repository
- Create a .env file in the root folder with PG_USER and PG_PASSWORD (for docker compose)
- Run the following command in the terminal: `docker compose up -d`
- Ensure that you have the same environment variables in the application.yml

### Spring Boot App

- If using Intellij you can set env vars in `Run/Debug Configurations`
- Run in IDE or however you want

## Endpoints (APIs)

-  TBD

## Dependencies

- All dependencies used are listed in `build.gradle`

## Testing

- TBD, not the focus of this project



# Deep dive in Spring Security

## Project Description

This project aims to explore Spring Security fundamentals, covering topics such as authentication, authorization, Spring Security Filter Chain, Basic Auth (might not be in repo at the end since I'll use JWT), JWT tokens and oAuth2 Resource server and using JDBC API since I have not used it before.

## Project Goals

- Learn Spring Security fundamentals
- Authentication & Authorization
- Spring Security Filter Chain
- Basic Auth (might not be in repo at the end since I'll use JWT)
- JWT tokens
- OAuth2 Resource server
- For the first time try out JDBC API instead of JPA and learn DAO pattern, etc.

## Datasource and Domain

- Local Postgres DB and Adminer as the DB manager
- User and Movie entities with a Many-to-Many relationship

## Project Structure

- Trying to follow best practices and organizing my code into different layers (DAOs, Repositories, Services, DTOs, etc)
- Its alot for a small project but a good learning experience for design patterns

## Installation

### DB
- Clone the repository
- Create a .env file in the root folder with PG_USER and PG_PASSWORD (for docker compose)
- Run the following command in the terminal: `docker compose up -d`
- Ensure that you have the same environment variables in the application.yml

### Spring Boot App

- If using Intellij you can set env vars in `Run/Debug Configurations`
- Run in IDE or however you want

## Endpoints (APIs) (Under Construction)

### OpenAPI Swagger Documentation
- To be added when all endpoints are done

### Users
- GET ../api/users -> <strong>All users in db</strong>
- GET ../api/users/{id} -> <strong>User by id</strong>
- GET ../api/users/{id}/movies -> <strong>Movie list By User Id</strong>
- POST ../api/users -> <strong>ex payload {"username":"test", "email": "test@test.com"}</strong>
- PUT ../api/users/{id} -> <strong>dynamic payload use both props or the one you want to update on resource</strong>

<em>If email exist in DB you will receive 400 and 'Email already exists'</em>

### Movies

- TBD, planning simple CRUD of Movie Resources

## Dependencies

- All dependencies used are listed in `build.gradle`

## Testing

- Not the focus of this project
- Might be some Unit / IT tests



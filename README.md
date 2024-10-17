# Spring API for BookStore

## Techs
- Spring Boot
- Spring Security
- Spring Data JPA
- Lombok
- Mapstruct
- Swagger UI
- MySQL

## Tools
- IntelliJ IDEA 2024
- Docker
- HeidiSQL

## Getting Started
1. Clone project: `git clone git@github.com:atuandev/bookstore-backend.git`
2. View all settings at `application.yml`
3. Install MySQL from Docker `docker pull mysql:8.4.0`
4. Run Container Docker `docker run --name mysql-8.4.0 -p 3307:3306 -e MYSQL_ROOT_PASSWORD=root -d mysql:8.4.0`

![image](https://github.com/user-attachments/assets/8983555b-0924-4956-8583-d9aa46621d18)
> Tutorial MySQL in Docker: https://www.youtube.com/watch?v=Oa7bpIZ6RxI&t=777s&ab_channel=Devteria

6. Use HeidiSQL to connect the database

![image](https://github.com/user-attachments/assets/bd02d5cd-69d3-47bc-9201-e583209b8f0d)

8. Use **Intellij** to run a project
9. Test API with **Postman** or **SwaggerUI** at `http://localhost:8080/bookstore/swagger-ui/index.html`

![image](https://github.com/user-attachments/assets/2278a8d4-2c85-4356-805b-59ad743a58a3)

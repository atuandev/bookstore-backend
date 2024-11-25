# Spring API for BookStore

## Requirement
- JDK 21

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
4. Run Container Docker `docker run --name mysql-8.4.0 -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -d mysql:8.4.0`

![image](https://github.com/user-attachments/assets/b59e4dd2-124a-4cce-86d5-2b5a1311af92)
> Tutorial MySQL in Docker: https://www.youtube.com/watch?v=Oa7bpIZ6RxI&t=777s&ab_channel=Devteria

6. Use HeidiSQL to connect the database with **username**: `root`, **password**: `root`

![image](https://github.com/user-attachments/assets/96e321da-49f6-4188-9724-74c2f3d759e1)

8. Use **Intellij** to run a project
9. Test API with **Postman** or **SwaggerUI** at `http://localhost:8080/bookstore/swagger-ui/index.html`

Login Admin account:
- username: `admin`
- password: `admin123`

![image](https://github.com/user-attachments/assets/5685947b-ee0b-4b32-b388-d5fdcd407861)

## Environment Variable
- MYSQL_PORT:... (default: 3306)
- MYSQL_USER: (default: root)
- MYSQL_PASS: (default: root)



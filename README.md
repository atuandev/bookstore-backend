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

### Prod mode
1. Clone project: `git clone git@github.com:atuandev/bookstore-backend.git`
2. Change profiles at `application.yml` to `prod`
3. Open terminal `mvn clean package`
4. Build Image `docker build -t api-image-bookstore .`
5. Run Container `docker-compose up -d`

### Dev mode
1. Clone project: `git clone git@github.com:atuandev/bookstore-backend.git`
2. Change profiles at `application.yml` to `dev`
3. Install MySQL from Docker `docker pull mysql:8.4.0`
4. Run Container Docker `docker run --name mysql-8.4.0 -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -d mysql:8.4.0`

![image](https://github.com/user-attachments/assets/b59e4dd2-124a-4cce-86d5-2b5a1311af92)
> Tutorial setup MySQL in Docker: https://www.youtube.com/watch?v=Oa7bpIZ6RxI&t=777s&ab_channel=Devteria

5. Use **Intellij** to run a project

### Database
- Use HeidiSQL to connect the database with **username**: `root`, **password**: `root`
- Import data in `/src/data/bookstore.sql`

![image](https://github.com/user-attachments/assets/96e321da-49f6-4188-9724-74c2f3d759e1)

### SwaggerUI 

1. Test API with **Postman** or **SwaggerUI** at `http://localhost:8080/bookstore/swagger-ui/index.html`
2. Login Admin account:
- username: `admin`
- password: `admin123`

![image](https://github.com/user-attachments/assets/8ab9e92e-47e5-40b8-ae04-bf97faaff592)


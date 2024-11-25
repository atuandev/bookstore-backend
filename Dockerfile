FROM openjdk:21

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} bookstore-api.jar

ENTRYPOINT ["java", "-jar", "/bookstore-api.jar"]

EXPOSE 8080
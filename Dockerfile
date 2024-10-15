FROM openjdk:21

ARG JAR_FILE=target/*.jar

ADD ${JAR_FILE} bookstore-api.jar

ENTRYPOINT ["java", "-jar", "/bookstore-api.jar"]

EXPOSE 8080
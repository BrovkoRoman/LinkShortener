FROM dockerhub.timeweb.cloud/library/openjdk:17-jdk-alpine

ARG JAR_FILE=build/libs/LinkShortener-0.0.1-SNAPSHOT.jar

COPY ./build/libs/LinkShortener-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]

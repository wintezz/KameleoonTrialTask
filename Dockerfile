FROM openjdk:17-alpine as build
MAINTAINER alex.petrov

COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .
COPY src src

RUN ./mvnw -B package

FROM openjdk:17-alpine as deploy

COPY --from=build  target/kameleoon-0.0.1.jar .

EXPOSE 8080
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar kameleoon-0.0.1.jar" ]
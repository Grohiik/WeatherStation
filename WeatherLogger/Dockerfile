# Build stage
FROM maven:3.8.1-openjdk-15-slim AS build
WORKDIR /app

COPY src src
COPY pom.xml .
# Build the jar package
RUN mvn clean install spring-boot:repackage

# Package stage
FROM openjdk:15.0.2-slim
WORKDIR /app
COPY --from=build /app/target/WeatherLogger*.jar /app/WeatherLogger.jar

EXPOSE 8080
CMD ["java", "-jar", "WeatherLogger.jar"]
# Build stage
FROM maven:3.9.6-eclipse-temurin-24-jammy AS build

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:24-jdk

WORKDIR /app

COPY --from=build /app/target/todos_app-*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
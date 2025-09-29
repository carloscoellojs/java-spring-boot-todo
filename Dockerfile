# Build stage
FROM eclipse-temurin:24-jdk AS build

RUN apt-get update && apt-get install -y maven

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:24-jre

WORKDIR /app

COPY --from=build /app/target/todos_app-*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
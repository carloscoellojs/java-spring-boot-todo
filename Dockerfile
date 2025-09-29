# Use OpenJDK 17 as base image
FROM openjdk:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the jar file
COPY target/todos_app-*.jar app.jar

# Expose port (change if your app uses a different port)
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
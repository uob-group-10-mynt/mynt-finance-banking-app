# Use an official Gradle image to build the application
FROM gradle:7.6.0-jdk17 AS build

# Set the working directory
WORKDIR /app

# Copy the project files to the Docker container
COPY --chown=gradle:gradle . .

# Build the application
RUN gradle build --no-daemon

# Use an OpenJDK runtime as a base image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file from the build stage to the runtime stage
COPY --from=build /app/build/libs/mynt-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the application runs on
EXPOSE 8080

# Set the entry point to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

# First Stage: Build
FROM maven:3.8-openjdk-17-slim as builder

# Install necessary build dependencies
RUN apt-get update && apt-get install -y libfreetype6 fontconfig

# Set working directory
WORKDIR /app

# Copy the Maven project files
COPY pom.xml ./

# Cache dependencies to speed up subsequent builds
RUN --mount=type=cache,target=/root/.m2/repository \
    mvn dependency:go-offline -B

# Copy the source code
COPY src ./src

# Build the application
RUN mvn package -DskipTests

# Second Stage: Runtime
FROM openjdk:17-jdk-slim

# Install runtime dependencies
RUN apt-get update && apt-get install -y libfreetype6 fontconfig

# Set working directory
WORKDIR /app

# Create uploads/files directory and set permissions
RUN mkdir -p uploads/files && chmod -R 755 uploads/files

# Copy the built JAR file from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose application port
EXPOSE 10000

# Run the application
CMD ["java", "-jar", "app.jar"]
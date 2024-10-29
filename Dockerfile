FROM maven:3.8-openjdk-17 as builder
WORKDIR /app
COPY pom.xml ./
RUN --mount=type=cache,target=/root/.m2/repository \
    mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]
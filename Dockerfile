FROM maven:3.8-openjdk-17 as builder
RUN apt-get update && apt-get install -y libfreetype6
WORKDIR /app
COPY pom.xml ./
RUN --mount=type=cache,target=/root/.m2/repository \
    mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 10000
CMD ["java", "-jar", "app.jar"]
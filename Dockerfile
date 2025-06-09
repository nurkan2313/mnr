# First Stage: Build
FROM maven:3.9.6-eclipse-temurin-21 as builder

RUN apt-get update && apt-get install -y libfreetype6 fontconfig

WORKDIR /app

COPY pom.xml ./
RUN --mount=type=cache,target=/root/.m2/repository \
    mvn dependency:go-offline -B

COPY src ./src
RUN mvn package

# Second Stage: Runtime
FROM eclipse-temurin:21-jdk

RUN apt-get update && apt-get install -y libfreetype6 fontconfig

WORKDIR /app
RUN mkdir -p uploads/files && chmod -R 755 uploads/files

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 10000

CMD ["java", "-jar", "app.jar"]

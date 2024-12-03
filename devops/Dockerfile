FROM maven:3.8.8-eclipse-temurin-17 AS builder
WORKDIR /app
RUN ls -la
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 3000

CMD ["java", "-jar", "app.jar"]
FROM maven:3.9.0-eclipse-temurin-17-alpine AS builder
WORKDIR /usermanagement/src/
COPY pom.xml ../
COPY src ./
RUN mvn -f /usermanagement/pom.xml clean package -DskipTests

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /usermanagement/lib
COPY --from=builder /usermanagement/target/UserManagement-0.0.1-SNAPSHOT.jar ./UserManagement.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/usermanagement/lib/UserManagement.jar"]
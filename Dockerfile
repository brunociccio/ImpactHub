# Build Stage
FROM ringcentral/maven:3.8.2-jdk17 as build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Package Stage
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/impacthub-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]




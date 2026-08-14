FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn -pl content-service -am clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/content-service/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
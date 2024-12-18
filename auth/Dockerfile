FROM gradle:8.8.0-jdk21 AS build
WORKDIR /app
COPY gradle gradle
COPY gradlew .
COPY build.gradle.kts settings.gradle.kts ./
RUN ./gradlew dependencies --no-daemon
COPY . .
RUN ./gradlew build --parallel --no-daemon -x test

FROM eclipse-temurin:21-jre-jammy AS runtime
WORKDIR /app
ENV JWT_SECRETS="default-secret"
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Djwt.secrets=${JWT_SECRETS}", "-jar", "app.jar"]

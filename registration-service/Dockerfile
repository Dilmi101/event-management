FROM docker.io/library/eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY gradlew settings.gradle build.gradle ./
COPY gradle ./gradle
RUN chmod +x gradlew && ./gradlew dependencies --no-daemon
COPY src ./src
COPY proto-repo /repo
RUN ./gradlew bootJar --no-daemon -x test

FROM docker.io/library/eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8083 9093
ENTRYPOINT ["java", "-jar", "app.jar"]

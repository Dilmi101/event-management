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
EXPOSE 8081 9091
HEALTHCHECK --interval=5s --timeout=3s --start-period=20s --retries=3 \
  CMD nc -vz 127.0.0.1 9090 || exit 1
ENTRYPOINT ["java", "-jar", "app.jar"]

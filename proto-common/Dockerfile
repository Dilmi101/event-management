FROM docker.io/library/eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY gradlew settings.gradle build.gradle ./
COPY gradle ./gradle
RUN chmod +x gradlew && ./gradlew dependencies --no-daemon
COPY src ./src
RUN ./gradlew publish --no-daemon && cp -r build/repo /repo

FROM scratch
COPY --from=build /repo /repo

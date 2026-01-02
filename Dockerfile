# -------- Build Stage --------
FROM gradle:9-jdk25 AS build
WORKDIR /app

# Nur das Nötigste zuerst kopieren (bessere Cache-Nutzung)
COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle settings.gradle ./
RUN ./gradlew dependencies --no-daemon

# Jetzt den Rest des Projekts
COPY . .
RUN ./gradlew clean bootJar -x test --no-daemon

# -------- Runtime Stage --------
FROM ibm-semeru-runtimes:open-25-jre
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

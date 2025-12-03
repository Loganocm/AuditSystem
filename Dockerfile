# ---- Build stage ----
# Use JDK 17 base and the project's Gradle Wrapper (Gradle 9.2.1) for compatibility
FROM eclipse-temurin:17-jdk AS build
WORKDIR /workspace

# Copy Gradle wrapper and build files first to leverage Docker layer caching
COPY gradlew settings.gradle build.gradle ./
COPY gradle ./gradle

# Ensure the Gradle wrapper is executable
RUN chmod +x ./gradlew

# Optionally warm up Gradle wrapper and dependency cache (non-fatal if no network cache)
RUN ./gradlew --version || true

# Now copy the rest of the source
COPY src ./src

# Build Spring Boot fat JAR (skip tests for faster image builds)
RUN ./gradlew clean bootJar -x test --no-daemon

# ---- Runtime stage ----
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /workspace/build/libs/*.jar /app/app.jar

# Configure JVM memory (tune as needed for your host/platform)
ENV JAVA_OPTS="-Xms256m -Xmx512m"
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]

## Multi-stage Dockerfile
## Builder: run the Gradle wrapper to produce the fat jar (bootJar)
FROM eclipse-temurin:25-jdk AS builder
WORKDIR /workspace

# Copy project files
COPY . /workspace

# Install small utilities required by the Gradle wrapper (xargs) and ensure wrapper is executable
# amazoncorretto base is Debian/Ubuntu-like so use apt-get

# Install utilities required by the Gradle wrapper
RUN apt-get update && apt-get install -y findutils ca-certificates curl unzip && rm -rf /var/lib/apt/lists/*

# Ensure wrapper is executable and build the jar (skip tests to speed up builds)
RUN chmod +x ./gradlew && \
	./gradlew clean bootJar --no-daemon -x test

## Runtime image: copy the jar from the builder stage into a small runtime image
FROM amazoncorretto:25-alpine
WORKDIR /app

# Copy the built jar (use wildcard to match the generated artifact)
COPY --from=builder /workspace/build/libs/*.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
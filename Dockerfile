FROM amazoncorretto:21-alpine

WORKDIR /app

# Copy gradle files
COPY gradle gradle
COPY gradlew .
COPY build.gradle.kts .
COPY settings.gradle.kts .

# Run the app
CMD ["./gradlew", "bootRun"]
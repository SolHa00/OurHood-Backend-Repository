# Build stage
FROM openjdk:17-slim AS builder

WORKDIR /app

RUN apt-get update && apt-get install -y findutils

COPY . .

RUN ./gradlew clean build -x test

# Run stage
FROM openjdk:17-slim

WORKDIR /app

COPY --from=builder ./app/build/libs/*.jar app.jar

CMD ["java", "-jar", "app.jar"]

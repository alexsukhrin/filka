# Stage 1: Build stage
FROM clojure:temurin-22-bookworm-slim as build

COPY . /app
WORKDIR /app
RUN clojure -T:build make-build

# Stage 2: Runtime stage
FROM eclipse-temurin:22-jre-jammy

# Install dependencies and set up the environment
RUN apt-get update \
    && apt-get install -y --no-install-recommends \
        curl \
    && rm -rf /var/lib/apt/lists/*


# Copy the application from the build stage
COPY --from=build /app/target/net.clojars.alexsukhrin/filka-0.1.0-SNAPSHOT.jar /app/filka.jar

WORKDIR /app

CMD ["java", "-jar", "filka.jar"]
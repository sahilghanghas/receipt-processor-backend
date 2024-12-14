# Use OpenJDK 23 as the base image
FROM openjdk:23-jdk-slim

# Set the working directory
WORKDIR /app

# Install required tools (e.g., wget, unzip for Gradle wrapper and dependencies)
RUN apt-get update && apt-get install -y wget unzip curl && rm -rf /var/lib/apt/lists/*

# Install Kotlin Compiler (optional, if using Kotlin features outside of Gradle)
RUN wget -q https://github.com/JetBrains/kotlin/releases/download/v1.9.25/kotlin-compiler-1.9.25.zip -O /tmp/kotlin-compiler.zip \
    && unzip /tmp/kotlin-compiler.zip -d /opt \
    && rm /tmp/kotlin-compiler.zip

# Add Kotlin compiler to PATH
ENV PATH="/opt/kotlin-compiler-1.9.25/bin:${PATH}"

# Copy project files into the container
COPY . .

RUN ls -l

# Ensure Gradle wrapper has execute permissions
RUN chmod +x ./gradlew

# Expose the application port (Micronaut default is 8080)
EXPOSE 8080

# Run the application using the Gradle wrapper
CMD ["./gradlew", "run"]
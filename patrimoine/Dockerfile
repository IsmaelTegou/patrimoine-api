# Étape de build avec Maven
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copier le POM et télécharger les dépendances en premier (pour un meilleur cache)
COPY pom.xml .
RUN mvn dependency:go-offline -B || mvn dependency:go-offline -B

# Copier le code source et construire
COPY src ./src
RUN mvn clean package -DskipTests

# Étape finale avec JRE seulement
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Installer curl pour les health checks
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Copier le jar compilé de l'étape de build
COPY --from=build /app/target/*.jar app.jar

# Create non-root user for security
RUN addgroup -g 1000 appuser && \
    adduser -D -u 1000 -G appuser appuser

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8080/api/actuator/health || exit 1

# Run as non-root user
USER appuser

# Exposer le port de l'application
EXPOSE 5014

# Start application with optimized JVM settings
ENTRYPOINT ["java", \
    "-XX:+UseContainerSupport", \
    "-XX:MaxRAMPercentage=75.0", \
    "-XX:+UseG1GC", \
    "-XX:MaxGCPauseMillis=200", \
    "-XX:InitiatingHeapOccupancyPercent=35", \
    "-Dspring.profiles.active=production", \
    "-Dserver.port=8080", \
    "-jar", \
    "app.jar"]

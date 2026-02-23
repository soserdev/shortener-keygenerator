# -------- Build Stage --------
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# -------- Runtime Stage --------
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=build /app/target/*jar app.jar

# Create non-root user
RUN addgroup -S spring && adduser -S spring -G spring \
    && mkdir /tmp \
    && chown -R spring:spring /app /tmp

USER spring:spring

EXPOSE 8080

ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
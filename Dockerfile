# syntax=docker/dockerfile:1

# Create a stage for resolving and downloading dependencies.
FROM eclipse-temurin:17-jdk-jammy as deps

WORKDIR /build

# Copy the mvnw wrapper with executable permissions.
COPY --chmod=0755 mvnw mvnw
COPY .mvn/ .mvn/

# Download dependencies as a separate step to take advantage of Docker's caching.
# Leverage a cache mount to /root/.m2 so that subsequent builds don't have to re-download packages.
RUN --mount=type=bind,source=pom.xml,target=pom.xml \
    --mount=type=cache,target=/root/.m2 ./mvnw dependency:go-offline -DskipTests

################################################################################

# Create a stage for building the application based on the stage with downloaded dependencies.
FROM deps as package

WORKDIR /build

COPY ./src src/

# Build the Spring Boot application, expecting a .war file as output.
RUN --mount=type=bind,source=pom.xml,target=pom.xml \
    --mount=type=cache,target=/root/.m2 \
    ./mvnw package -DskipTests && \
    mv target/$(./mvnw help:evaluate -Dexpression=project.artifactId -q -DforceStdout)-$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout).war target/app.war

################################################################################

# Create a new stage for running the application using a Tomcat base image.
# Tomcat is required to run WAR files.
# We use 'tomcat:10.1-jre17-temurin-jammy' which includes JRE 17.

FROM tomcat:10.1-jre17-temurin-jammy AS final

# Copy the built WAR file from the 'package' stage into Tomcat's webapps directory.
# When a WAR file is placed in 'webapps', Tomcat automatically deploys it.
COPY --from=package /build/target/app.war /usr/local/tomcat/webapps/ROOT.war

# Expose the default Tomcat port.
EXPOSE 8080

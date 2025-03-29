# Stage 1: Build Stage
FROM openjdk:11-jdk-slim AS build

# Install required dependencies
RUN apt-get update && \
    apt-get install -y wget unzip && \
    apt-get clean

# Install Gradle 7.6 (compatible with Spring Boot 2.6.1)
RUN wget https://services.gradle.org/distributions/gradle-7.6-bin.zip -P /tmp && \
    unzip /tmp/gradle-7.6-bin.zip -d /opt && \
    rm /tmp/gradle-7.6-bin.zip && \
    ln -s /opt/gradle-7.6/bin/gradle /usr/local/bin/gradle

# Verify Gradle installation
RUN gradle --version

# Set the working directory inside the container
WORKDIR /app

# Copy the Gradle build files
COPY build.gradle settings.gradle /app/

# Copy the entire project into the container
COPY . /app/

# Run Gradle to build the project and create the JAR
RUN gradle build  --no-daemon --warning-mode all  -x test

# Stage 2: Final Stage
FROM openjdk:11-jre-slim

# Set the working directory for the final image
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/build/libs/*.jar /app/app.jar

# Expose the port on which the application will run
EXPOSE 8081

# Set the default command to run the JAR file
CMD ["java", "-jar", "app.jar"]






## Stage 1: Build Stage (Development with Live Reload)
#FROM adoptopenjdk/openjdk11:jdk-11.0.8_10-debian-slim AS dev
#WORKDIR /app
#
## Copy Gradle configuration files
#COPY build.gradle /app
#COPY settings.gradle /app
#
## Pre-download dependencies for caching
#RUN gradle build --no-daemon
#
## Copy source files
#COPY src /app/src
#
## Expose the application port
#EXPOSE 8080
#
## Enable live reload with Spring Boot DevTools
#CMD ["gradle", "bootRun", "--no-daemon", "-Dspring.devtools.restart.enabled=true"]
#
## Stage 2: Runtime Stage (Optimized Production Image)
#FROM openjdk:21-jdk-slim AS prod
#WORKDIR /app
#
## Copy the built JAR from the build stage
#COPY --from=dev /app/build/libs/*.jar app.jar
#
## Expose the application port
#EXPOSE 8080
#
## Run the application
#CMD ["java", "-jar", "app.jar"]

#FROM adoptopenjdk/openjdk11:jdk-11.0.8_10-debian-slim
#VOLUME /tmp
#EXPOSE 8081
#ARG JAR_FILE=build/libs/*.jar
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]
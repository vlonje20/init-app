# Use a base image with Java runtime environment
FROM openjdk:11-jre-slim

# Set the working directory
WORKDIR /app

# Copy the WAR file to the container
COPY target/jenkins-docker-init-app.war /app/jenkins-docker-init-app.war

# Expose the port your app runs on
EXPOSE 8080

# Command to run the WAR file
ENTRYPOINT ["java", "-jar", "/app/jenkins-docker-init-app.war"]

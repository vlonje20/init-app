# Use a base image with Java runtime environment
FROM openjdk:11-jre-slim

# Set the working directory
WORKDIR /app

# Copy the WAR file to the container
COPY vin-init-app.war /app/vin-init-app.war

# Expose the port your app runs on
EXPOSE 8080

# Command to run the WAR file
ENTRYPOINT ["java", "-jar", "/app/vin-init-app.war"]

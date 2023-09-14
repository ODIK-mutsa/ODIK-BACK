# Base image
FROM openjdk:17-jdk

# Command to run the application
# CMD ["java", "-jar", "odik.jar"]

ARG JAR_FILE_PATH=build/libs/*.jar

# Copy the application JAR file
COPY ${JAR_FILE_PATH} odik-0.0.1-SNAPSHOT.jar

# Expose the port the application runs on
EXPOSE 8080


ENTRYPOINT ["java", "-jar", "odik-0.0.1-SNAPSHOT.jar"]


FROM openjdk:17-jdk-slim-buster
WORKDIR /app

COPY target/appwoyo-1.0-SNAPSHOT.jar /app/appwoyo-1.0-SNAPSHOT.jar


ENTRYPOINT java -jar /app/appwoyo-1.0-SNAPSHOT.jars
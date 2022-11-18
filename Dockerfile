FROM openjdk:17-jdk-slim-buster
WORKDIR /app

COPY target/appwoyo-1.0-SNAPSHOT.jar /appwoyo-1.0-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","-Xms128m -Xmx1024m -XX:PermSize=64m -XX:MaxPermSize=256m","/appwoyo-1.0-SNAPSHOT.jar"]

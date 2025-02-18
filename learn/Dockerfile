#FROM openjdk:17-jdk-alpine
#ADD target/learn-0.0.1-SNAPSHOT.jar app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]

# Stage 1: build
# Start with a Maven image that includes JDK 21
FROM maven:3.9.8-amazoncorretto-21 AS build

# Copy source code and pom.xml file to /app folder
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build source code with maven
RUN mvn package -DskipTests

#Stage 2: create image
# Start with Amazon Correto JDK 21
FROM amazoncorretto:21.0.4

# Set working folder to App and copy complied file from above step
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

#docker run --name mysql-container --network net1802 -e MYSQL_ROOT_PASSWORD=root -p 33065:3306 mysql
#docker run --name blogpapi --network net1802 -p 8080:8080 -d blogapi
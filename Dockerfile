#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY . /
RUN mvn -f /pom.xml clean package spring-boot:repackage
EXPOSE 8080
ENTRYPOINT ["mvn","spring-boot:run"]

#
# Package stage
#
#FROM openjdk:11-jre-slim
#COPY --from=build /target/webstore-0.0.1.jar /webstore-0.0.1.jar
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","/usr/local/lib/demo.jar"]

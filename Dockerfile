FROM openjdk:17-jdk-alpine
COPY build/libs/hairbnb-1.0.0.jar hairbnb-1.0.0.jar
ENTRYPOINT ["java","-jar","/hairbnb-1.0.0.jar"]
FROM gradle:8.8.0-jdk21-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build

FROM eclipse-temurin:21
COPY --from=build  /home/gradle/src/build/libs/hairbnb-0.0.1.jar hairbnb.jar
ENTRYPOINT ["java", "-jar", "hairbnb.jar"]
EXPOSE 8080

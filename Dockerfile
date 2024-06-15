FROM eclipse-temurin:21
COPY build/libs/hairbnb-1.0.0.jar hairbnb.jar
ENTRYPOINT ["java", "-jar", "hairbnb.jar"]
FROM openjdk:11
LABEL maintainer = "Pratyush"
ADD target/NewsAPI-0.0.1-SNAPSHOT.jar docker-image-newsapi.jar
ENTRYPOINT ["java", "-jar", "docker-image-newsapi.jar"]
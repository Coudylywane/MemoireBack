#FROM eclipse-temurin:17-jdk-alpine
#VOLUME /tmp
#ARG JAR_FILE=*.jar
#COPY ${JAR_FILE} memoire.jar
#ENTRYPOINT ["java", "-jar", "memoire.jar"]
# Utilisez une image de base avec Java et Maven préinstallés
FROM adoptopenjdk:17-jdk-alpine AS builder
FROM maven:3.8.4-openjdk-17 AS maven
# Définissez le répertoire de travail dans l'image
WORKDIR /app

# Copiez le fichier JAR construit dans l'image
COPY ./construction-0.0.1-SNAPSHOT.jar construction-0.0.1-SNAPSHOT.jar
RUN mvn clean package
# Copiez le fichier de configuration dans l'image
COPY src/main/resources/application.properties application.properties

# Expose the port that the application will run on
EXPOSE 8091

# Exécutez l'application Spring Boot lorsque le conteneur démarre
ENTRYPOINT ["java", "-jar", "construction-0.0.1-SNAPSHOT.jar"]

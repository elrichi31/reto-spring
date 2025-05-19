# Usa una imagen de Java
FROM openjdk:21-jdk-slim

# Copia el JAR construido (asegúrate de usar el nombre correcto)
ARG JAR_FILE=target/reto01-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# Expone el puerto de tu app
EXPOSE 8080

# Ejecuta la app
ENTRYPOINT ["java", "-jar", "/app.jar"]

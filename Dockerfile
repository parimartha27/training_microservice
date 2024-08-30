#Gunakan image dasar Java
FROM openjdk:17-jdk-slim

# Buat direktori kerja
WORKDIR /app

COPY build/libs/restful_api-0.0.1-SNAPSHOT.jar metal.jar
COPY keystore.p12 keystore.p12

EXPOSE 8085

ENTRYPOINT ["java", "-jar", "metal.jar"]



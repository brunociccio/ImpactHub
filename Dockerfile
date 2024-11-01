# Usando uma imagem base do OpenJDK
FROM openjdk:17-jdk-alpine

# Diretório de trabalho no contêiner
WORKDIR /app

# Copia o arquivo JAR da aplicação para o contêiner
COPY target/impacthub-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta em que a aplicação irá rodar
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]

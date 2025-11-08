# Usa o Maven para compilar o código e gerar o JAR
FROM maven:3.9.5-eclipse-temurin-21-alpine AS build

WORKDIR /app

# Copia os arquivos de configuração do Maven e as fontes
COPY pom.xml .
COPY src /app/src

# Compila o projeto e gera o JAR (pulando testes)
RUN mvn clean package -DskipTests

# Usa uma imagem JRE (ambiente de execução Java) menor para otimizar o tamanho
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copia o JAR compilado do estágio anterior para a imagem final
COPY --from=build /app/target/*.jar app.jar


EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

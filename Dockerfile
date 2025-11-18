# ============================
# 1. ETAPA DE BUILD (MAVEN)
# ============================
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copia o pom.xml primeiro para resolver dependências
COPY pom.xml .
RUN mvn dependency:resolve

# Copia o código-fonte
COPY src ./src

# Build do jar
RUN mvn clean package -DskipTests


# ============================
# 2. ETAPA DE EXECUÇÃO (JDK)
# ============================
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copia o jar gerado
COPY --from=build /app/target/helplink-api-1.0.0.jar app.jar

# Porta padrão da API
EXPOSE 8080

# ENTRYPOINT para rodar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

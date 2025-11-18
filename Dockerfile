# ============================
# 1. ETAPA DE BUILD (MAVEN)
# ============================
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copiar pom.xml e baixar dependências
COPY pom.xml .
RUN mvn -q dependency:go-offline

# Copiar todo o projeto
COPY src ./src

# Build (gera o jar executável)
RUN mvn clean package -DskipTests

# ============================
# 2. ETAPA DE EXECUÇÃO
# ============================
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copiar o JAR gerado
COPY --from=build /app/target/helplink-api-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]

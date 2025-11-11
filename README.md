# HelpLink API

API REST para plataforma de doações que conecta doadores com ONGs e abrigos.

## Requisitos

- Java 17+
- Maven 3.6+

## Tecnologias

- Spring Boot 3.1.5
- Spring Security + JWT
- Spring Data JPA
- OpenAPI 3 / Swagger UI
- H2 Database (desenvolvimento)
- Lombok

## Começando

### Executar Localmente

\`\`\`bash
mvn spring-boot:run
\`\`\`

A aplicação iniciará em `http://localhost:8080/api`

### Acessar Swagger UI

\`\`\`
http://localhost:8080/api/swagger-ui.html
\`\`\`

### H2 Console (Desenvolvimento)

\`\`\`
http://localhost:8080/api/h2-console
\`\`\`

## 🔐 Autenticação JWT

### 1. Login e obter token

\`\`\`bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"user@test.com","senha":"password123"}'
\`\`\`

Resposta:
\`\`\`json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
\`\`\`

### 2. Usar token em requisições protegidas

\`\`\`bash
curl -X GET http://localhost:8080/api/usuarios/1 \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
\`\`\`

## 📚 Endpoints da API

### Autenticação (Público)
- `POST /auth/login` - Fazer login
- `POST /auth/registrar` - Registrar novo usuário

### Usuários (Protegido)
- `GET /usuarios` - Listar usuários
- `GET /usuarios/{id}` - Buscar usuário por ID
- `POST /usuarios` - Criar usuário
- `PUT /usuarios/{id}` - Atualizar usuário
- `DELETE /usuarios/{id}` - Deletar usuário

### Instituições (Público)
- `GET /instituicoes` - Listar todas as ONGs
- `GET /instituicoes/{id}` - Buscar ONG específica
- `POST /instituicoes` - Criar instituição

### Categorias (Público)
- `GET /categorias` - Listar categorias
- `POST /categorias` - Criar categoria

### Itens (Protegido)
- `GET /itens` - Listar itens
- `POST /itens` - Criar item
- `PUT /itens/{id}` - Atualizar item
- `DELETE /itens/{id}` - Deletar item

### Doações (Protegido)
- `GET /doacoes` - Listar doações
- `POST /doacoes` - Criar doação
- `PUT /doacoes/{id}` - Atualizar doação
- `POST /doacoes/{id}/confirmar` - Confirmar doação

## 🏗 Arquitetura

\`\`\`
com/fiap/helplink/
├── config/              # JWT, Security, OpenAPI
├── controller/          # REST Controllers
├── model/              # JPA Entities
├── repository/         # Spring Data Repositories
├── service/            # Business Logic
├── dto/                # Data Transfer Objects
└── HelpLinkApplication # Main Class
\`\`\`

## 📋 Banco de Dados (Oracle/H2)

Tabelas com prefixo `TB_HELPLINK_`:
- TB_HELPLINK_USUARIO
- TB_HELPLINK_INSTITUICAO
- TB_HELPLINK_CATEGORIA
- TB_HELPLINK_ITEM
- TB_HELPLINK_DOACAO
- TB_HELPLINK_DOACAO_ITEM
- TB_HELPLINK_AGENDAMENTO
- TB_HELPLINK_IMPACTO
- TB_HELPLINK_VOLUNTARIADO
- TB_HELPLINK_ENDERECO
- TB_HELPLINK_BAIRRO
- TB_HELPLINK_CIDADE
- TB_HELPLINK_ESTADO
- TB_HELPLINK_PAIS

## Configuração JWT

Arquivo: `src/main/resources/application.yml`

\`\`\`yaml
app:
  jwt:
    secret-key: MyVeryLongAndSecureSecretKeyForJWTTokenSigningThatMustBeAtLeast256BitsLongForHS256Algorithm!
    expiration: 86400000      # 24 horas
    refresh-expiration: 604800000  # 7 dias
\`\`\`

## Autores

FIAP - Turma 2TDSPW
- Arthur Ramos dos Santos (RM558798)
- Felipe Melo de Sousa (RM556099)
- Robert Daniel da Silva Coimbra (RM555881)

Global Solution 2025 - O Futuro do Trabalho

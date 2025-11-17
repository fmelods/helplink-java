# 🟦 HelpLink – Plataforma de Doações, Instituições e Voluntariado

## 👥 Integrantes
- Arthur Ramos – RM558798
- Felipe Melo – RM556099
- Robert Coimbra – RM555881

## 📌 Descrição Geral
O **HelpLink** é uma plataforma completa criada para conectar **doadores**, **instituições sociais** e **voluntários**, facilitando o processo de ajuda humanitária.  
Inclui API REST, dashboard administrativo, autenticação JWT, sistema de impacto social e gerenciamento completo de doações.

## 🧱 Arquitetura da Aplicação
```
helpLink
 ├── controller/        → Controladores REST e Site
 ├── dto/               → Objetos de transferência
 ├── model/             → Entidades JPA
 ├── repository/        → Repositórios
 ├── service/           → Regras de negócio
 ├── security/          → JWT, filtros, permissões
 └── resources/
       ├── templates/   → Site Thymeleaf
       └── messages/    → Internacionalização
```

## ⚙️ Tecnologias Utilizadas
- Java 17
- Spring Boot
- Spring MVC
- Spring Security + JWT
- Spring Data JPA
- Lombok
- Swagger / OpenAPI
- PostgreSQL
- Thymeleaf

## 🔐 Segurança (JWT)
- Login via token
- Filtro JWT (`JwtAuthenticationFilter`)
- Provider de token (`JwtTokenProvider`)
- Sessão Stateless

Exemplo de uso:
```
Authorization: Bearer <token>
```

## 🎁 Módulo de Doações
- CRUD completo
- Fluxo por status: **ABERTA → CONCLUIDA / CANCELADA**
- Geração automática de impacto ao concluir
- Associação com usuários e instituições
- Itens vinculados à doação

## 🧍 Usuários
- Cadastro e login
- Autenticação JWT
- Perfis diferentes
- Associação com doações e voluntariado

## 🏢 Instituições
- Cadastro
- Listagem pública
- Associação com doações e agendamentos

## 🗄️ Banco de Dados
Principais entidades:
- Usuario
- Doacao
- Instituicao
- DoacaoItem
- Impacto
- Voluntariado
- Agendamento

Relacionamentos:
- 1 Usuário → N Doações
- 1 Instituição → N Doações
- 1 Doação → N Itens
- 1 Doação → 1 Impacto

## 📚 Endpoints Principais
### Autenticação
- POST `/auth/login`
- POST `/auth/registrar`

### Doações
- GET `/doacoes`
- POST `/doacoes`
- PUT `/doacoes/{id}/status`
- DELETE `/doacoes/{id}`

### Instituições
- GET `/instituicoes`
- POST `/instituicoes`

### Itens
- GET `/itens`
- POST `/itens`

## 🚀 Como Executar
Clone:
```
git clone <repo>
```

Configure o banco no `application.properties`.

Execute:
```
mvn spring-boot:run
```

Acesse o Swagger:
```
http://localhost:8080/swagger-ui.html
```

## 🏆 Conclusão
O HelpLink é uma plataforma completa de impacto social, construída para facilitar o processo de doação e voluntariado, oferecendo API segura, site integrado e arquitetura moderna.

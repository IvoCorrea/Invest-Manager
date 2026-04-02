# Invest Manager API

API REST para gestão de usuários e carteiras de investimento, construída com **Java 21 + Spring Boot**. O projeto oferece autenticação com JWT, CRUD de usuários e carteira, e integração com a Brapi para consulta de cotações.

## ✨ Funcionalidades

- Cadastro e autenticação de usuário (`/auth/register` e `/auth/login`)
- Autorização com token JWT para rotas protegidas
- CRUD de usuário (`/user`)
- CRUD de carteira (`/portfolio`)
- Inclusão, atualização e remoção de ativos em carteira
- Consulta de preço por ticker via Brapi (`/prices/{ticker}`)

## 🧱 Stack técnica

- Java 21
- Spring Boot 3.5.x
- Spring Web
- Spring Data JPA
- Spring Security
- JWT (`jjwt`)
- WebClient (Spring WebFlux) para integração externa
- PostgreSQL
- Maven

## 📁 Estrutura do projeto

```text
src/main/java/com/ivocorrea/investmanager
├── config        # Segurança e filtro JWT
├── controller    # Endpoints REST
├── dto           # Objetos de entrada/saída
├── entity        # Entidades JPA
├── exception     # Tratamento centralizado de erros
├── repository    # Repositórios Spring Data
└── service       # Regras de negócio
```

## 🔐 Variáveis de ambiente / propriedades

A aplicação espera as seguintes propriedades:

- `jwt.secret` (chave para assinar/validar JWT)
- `brapi.token` (token da API Brapi)
- `brapi.url` (URL base da Brapi)
- Configurações padrão de banco Spring (`spring.datasource.*`, `spring.jpa.*`)

Exemplo usando variáveis de ambiente (Linux/macOS):

```bash
export JWT_SECRET="uma-chave-longa-e-segura-com-32-bytes-ou-mais"
export BRAPI_TOKEN="seu-token"
export BRAPI_URL="https://brapi.dev/api"
export SPRING_DATASOURCE_URL="jdbc:postgresql://localhost:5432/investmanager"
export SPRING_DATASOURCE_USERNAME="postgres"
export SPRING_DATASOURCE_PASSWORD="postgres"
```

> Dica: padronize um `application.properties` (ou `application.yml`) para facilitar setup local e ambientes.

## ▶️ Como executar localmente

### Pré-requisitos

- Java 21
- Maven 3.9+
- PostgreSQL rodando localmente ou via Docker

### 1) Subir banco com Docker (opcional)

```bash
docker run --name investmanager-db \
  -e POSTGRES_DB=investmanager \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -p 5432:5432 \
  -d postgres:16
```

### 2) Executar aplicação

```bash
./mvnw spring-boot:run
```

Ou gerar artefato e executar:

```bash
./mvnw clean package
java -jar target/investmanager-0.0.1-SNAPSHOT.jar
```

## 📌 Endpoints principais

### Auth

- `POST /auth/register` — cadastra usuário
- `POST /auth/login` — autentica e retorna JWT

### Usuário

- `GET /user/{userId}`
- `GET /user`
- `PUT /user/{userId}`
- `DELETE /user/{userId}`

### Carteira

- `POST /portfolio`
- `GET /portfolio/{portfolioId}`
- `GET /portfolio`
- `DELETE /portfolio/{portfolioId}`
- `PATCH /portfolio/{portfolioId}/asset`
- `PATCH /portfolio/{portfolioId}/asset/{assetId}`
- `DELETE /portfolio/{portfolioId}/asset/{assetId}`

### Cotações

- `GET /prices/{ticker}`

## 🧪 Testes

```bash
./mvnw test
```

## 🗺️ Próximos passos sugeridos

- Adicionar documentação OpenAPI/Swagger
- Implementar validações com Bean Validation (`@Valid`, `@NotBlank`, etc.)
- Melhorar rastreabilidade com logs estruturados
- Aumentar cobertura de testes unitários e de integração
- Criar perfil `dev` com dados e configurações padrão

## 📄 Licença

Defina uma licença (ex.: MIT) para facilitar contribuição e uso do projeto.

# 🎬 Movie Catalog API

[![Java](https://img.shields.io/badge/Java-21+-red.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-Build-blue.svg)](https://maven.apache.org/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17+-blue.svg)](https://www.postgresql.org/)
[![MongoDB](https://img.shields.io/badge/MongoDB-7+-green.svg)](https://www.mongodb.com/)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](LICENSE)

API REST desenvolvida em **Spring Boot** para gerenciamento de um **catálogo de filmes**.  
O projeto foi criado com foco em **boas práticas de backend**, incluindo **arquitetura em camadas**, **validações**, **tratamento centralizado de exceções**, **paginação**, **logs**, e **testes unitários**, servindo como base de estudo ou template para APIs REST modernas em Java.

---

## 🚀 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3+**
- **Spring Web**
- **Spring Data JPA**
- **Spring Data MongoDB**
- **PostgreSQL**
- **MongoDB**
- **Maven**
- **JUnit 5**
- **Mockito**

---

## 🧱 Arquitetura do Projeto

O projeto segue o padrão de **arquitetura em camadas**, separando responsabilidades e facilitando manutenção e testes:

Controller → Service → Repository

### 📁 Estrutura de Pacotes

```
com.hendersonkleber.moviecatalog
├── controller
├── domain
├── dto
├── exception
├── repository
├── service
```

---

## 🔗 Endpoints Principais

### 🎥 Filmes

- `GET /movies`
- `GET /movies/{id}`
- `POST /movies`
- `PUT /movies/{id}`
- `DELETE /movies/{id}`

### 🎥 Comentários

- `GET /reviews`
- `POST /reviews`
- `DELETE /reviews/{id}`

---

## ⚠️ Tratamento de Erros

A API utiliza **Problem Details (RFC 7807)** para respostas de erro padronizadas, incluindo:

- Erros de validação
- Recursos não encontrados
- Conflitos de dados
- Erros internos

---

## 🧪 Testes

Para executar os testes unitários:

```bash
./mvnw test
```

---

## ▶️ Executando o Projeto

1. Clone o repositório:
```bash
git clone https://github.com/hendersonkleber/movie-catalog-api.git
```

2. Configure o banco de dados (PostgreSQL e MongoDB) no `application.yml`

3. Execute a aplicação:
```bash
./mvnw spring-boot:run
```

---

## 👨‍💻 Autor

Desenvolvido por **Henderson Kleber**

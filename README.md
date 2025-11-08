# API de Clima e Integração

![Java](https://img.shields.io/badge/Java-21-blue?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.7-green?style=for-the-badge&logo=spring)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?style=for-the-badge&logo=postgresql)
![Docker](https://img.shields.io/badge/Docker-blue?style=for-the-badge&logo=docker)
![Maven](https://img.shields.io/badge/Maven-red?style=for-the-badge&logo=apache-maven)

> API RESTful para consulta de dados climáticos, construída como parte de um desafio técnico. A aplicação extrai dados da API OpenWeather, armazena-os em um banco de dados PostgreSQL e os expõe através de endpoints para consulta.

---

## Sumário

* [Estrutura de Diretórios](#estrutura-de-diretórios)
* [Tecnologias Utilizadas](#-tecnologias-utilizadas)
* [Como Executar o Projeto](#como-executar-o-projeto)
* [Uso da API](#-uso-da-api)
* [Testes](#-testes)

---

## Estrutura de Diretórios

O projeto segue uma arquitetura em camadas e está organizado da seguinte forma:
```
dev.felipe.climaapi/
  ├── controlador/      # Camada de API REST 
  ├── dto/              # Records para DTOs  
  ├── entidade/         # Entidade JPA       
  ├── excecao/          # Tratamento de Erros Global 
  ├── integracao/       # Cliente HTTP OpenFeign 
  ├── mapper/           # Mapeador de Objetos 
  ├── repositorio/      # Repositórios Spring Data JPA 
  └── servico/          # Lógica de Negócio 
```

---

## 🛠️ Tecnologias Utilizadas

*   **Backend:** Java 21, Spring Boot 3
*   **Persistência:** Spring Data JPA, Hibernate
*   **Banco de Dados:** PostgreSQL 16
*   **Migrações:** Flyway
*   **Cliente HTTP:** Spring Cloud OpenFeign
*   **Testes:** JUnit 5, Mockito, H2, AssertJ
*   **Documentação:** SpringDoc (Swagger UI)
*   **Build:** Maven
*   **Conteinerização:** Docker, Docker Compose, WSL 2
*   **Utilitários:** Lombok

---


## Como Executar o Projeto

Siga estes passos para configurar e executar a aplicação localmente.

### 1. Pré-requisitos

*   [Git](https://git-scm.com/downloads)
*   [JDK 21](https://www.oracle.com/java/technologies/downloads/#jdk21)
*   [Docker](https://www.docker.com/products/docker-desktop/) e [Docker Compose](https://docs.docker.com/compose/install/)

<br/>

### 2. Clonar o Repositório

```
 git clone https://github.com/filipeluisgg/api-clima-integracao.git
 cd clima-api
```
<br/>

### 3. Configuração da API Key

Para que a aplicação possa se conectar à OpenWeather, você precisa de uma chave de API.

1.  Gratuitamente crie uma conta no [OpenWeatherMap](https://openweathermap.org/appid) e gere uma chave de API.
2.  Crie uma **variável de ambiente** no seu sistema chamada `OPENWEATHER_API_KEY` e atribua a ela o valor da sua chave.

*   **Exemplo (Linux/macOS):**
    ```bash
    export OPENWEATHER_API_KEY="sua_chave_aqui"
    ```
*   **Exemplo (Windows PowerShell):**
    ```shell
    $env:OPENWEATHER_API_KEY="sua_chave_aqui"
    ```

A aplicação está configurada para ler esta variável.

<br/>

### 4. Subir o Banco de Dados

O projeto usa Docker Compose para gerenciar o contêiner do PostgreSQL, na raiz do projeto, execute:
```bash
  docker compose up -d
```
Este comando irá baixar a imagem do Postgres e iniciar o banco de dados em segundo plano.

<br/>

### 5. Executar a Aplicação

Com o banco de dados rodando, inicie a aplicação Java. Se a variável de ambiente 'OPENWEATHER_API_KEY' não foi definida anterior mente no passo 3, a aplicação irá falhar ao tentar iniciar. Use o comando Maven Wrapper abaixo para iniciar a API:

```bash
  # Em Linux/macOS
  ./mvnw spring-boot:run

  # Em Windows (CMD ou PowerShell)
  .\mvnw.cmd spring-boot:run
```


A API e sua documentação estarão disponíveis em `http://localhost:8080/swagger-ui.html`.

---

## 📃 Uso da API

A melhor forma de explorar a API é através da documentação interativa do Swagger.

*   **URL da Documentação:** [**http://localhost:8080/swagger-ui.html**](http://localhost:8080/swagger-ui.html)

### Endpoints disponíveis

| Método | Endpoint         | Descrição                                                                 |
|--------|------------------|---------------------------------------------------------------------------|
| `POST` | `/clima`         | Busca dados climáticos por latitude/longitude e os armazena no banco.     |
| `GET`  | `/clima`         | Consulta o histórico de dados salvos para um local específico.            |
| `GET`  | `/clima/todos`   | Lista todos os registros climáticos salvos no banco de dados.             |

---

## ✅ Testes

O projeto possui uma suíte de testes automatizados para as camadas de Controller, Service e Repository.

Para executar todos os testes, utilize o comando:

```bash
  # Em Linux/macOS
  ./mvnw test

  # Em Windows (CMD ou PowerShell)
  .\mvnw.cmd test
```

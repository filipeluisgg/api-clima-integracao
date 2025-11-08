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
* [Executando a API em Docker](#executar-a-api-em-docker)
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

## Executar a API em Docker

### 1. Pré-requisitos

*   [Git](https://git-scm.com/downloads)
*   [JDK 21](https://www.oracle.com/java/technologies/downloads/#jdk21)
*   [Docker](https://www.docker.com/products/docker-desktop/) e [Docker Compose](https://docs.docker.com/compose/install/)

<br/>

### 2. Clonar o Repositório
Use o `PowerShell` se estiver no Windows. Os comandos a seguir devem ser todos executados com permissões de administrador.
```
 git clone https://github.com/filipeluisgg/api-clima-integracao.git
 cd api-clima-integracao
```

<br/>

### 3. Configuração da API Key

Para fazer o build do código Java e rodar a aplicação será necessário que você gratuitamente crie uma conta no [OpenWeatherMap](https://openweathermap.org/appid) e gere uma chave de API.
O seguinte comando é para definir a variável de ambiente com a chave de API, caso contrário a aplicação não funcionará corretamente. No comando, troque o valor "sua-chave-api-aqui" para o real valor da chave de API que gerou.

Se estiver no Windows, execute no powershell:

```shell
  $env:OPENWEATHER_API_KEY="sua-chave-api-aqui"
```

Se estiver no Linux ou MacOS, execute estes dois comandos:

```bash
  chmod +x mvnw
  export OPENWEATHER_API_KEY="sua-chave-api-aqui"
```

<br/>

### 4. Build do Projeto

O comando abaixo deve ser executado no mesmo terminal que foi definido a variável de ambiente no passo anterior. Este comando constrói a imagem Docker da API (compilando o código Java) e inicia o ambiente completo (API + PostgreSQL). Com isso, o serviço estará em contêiner e apto para ser executado em qualquer máquina.

```
  docker compose up --build -d
```

Após o término da execução, a API e sua documentação estarão disponíveis em `http://localhost:8080/swagger-ui.html`.

---

## 📃 Uso da API

A melhor forma de explorar a API é através da documentação interativa do Swagger.

*   **URL da Documentação:** [**http://localhost:8080/swagger-ui.html**](http://localhost:8080/swagger-ui.html)
*   **Obs**: Para **consultar coordenadas**, recomendo o site https://www.mapcoordinates.net/pt

### Endpoints disponíveis

| Método | Endpoint         | Descrição                                                                 |
|--------|------------------|---------------------------------------------------------------------------|
| `POST` | `/clima`         | Busca dados climáticos por latitude/longitude e os armazena no banco.     |
| `GET`  | `/clima`         | Consulta o histórico de dados salvos para um local específico.            |
| `GET`  | `/clima/todos`   | Lista todos os registros climáticos salvos no banco de dados.             |

---

## ✅ Testes

O projeto possui suítes de testes automatizados para as camadas de Controller, Service e Repository.
Para executar os testes, execute os comandos abaixo como administrador e pelo mesmo terminal que usou para subir a API em Docker:

Para Windows (CMD ou PowerShell):
```shell
  .\mvnw.cmd test
```

Para Linux/macOS, execute estes dois comandos:
```bash
  chmod +x mvnw
  ./mvnw test
```

# Customer management

A aplicação tem o intuito de manipulação de dados de um cliente. Para isso é necessário que seja possível criar, editar, listar e atualizar os dados por completo e também de forma granular.

# Requisitos necessários:

* Permita criação de novos clientes;

* Permita a atualização de clientes existentes;

* Permita que seja possível listar os clientes de forma paginada;

* Permita que buscas por atributos cadastrais do cliente;

* É necessário também que cada elemento retornado pela API de clientes informe a idade;

# Endpoints

Os principais endpoints da API são:

* Criar um cliente: POST /customer-management/v1/customers
* Atualizar um cliente: PUT /customer-management/v1/customers
* Deletar um cliente (pelo id): DELETE /customer-management/v1/customers/1
* Encontrar um cliente pelo ID: GET /customer-management/v1/customers/1
* Filtrar cliente por nome: GET /customer-management/v1/customers/byName/{name}
* Filtrar cliente por documento: GET /customer-management/v1/customers/byDocument/{document}
* Filtrar cliente pelo e-amil: GET /customer-management/v1/customers/byEmail/{email}
* Filtrar cliente por data de nascimento: GET /customer-management/v1/customers/byBirthDate/{birthDate}
* Buscar clientes pela data de cadastro (ordenados e paginados): GET /customer-management/v1/customers?startDate=2021-10-01&endDate=2021-10-15&page=3&size=5&sort=DESC

# Tecnologias utilizadas

* **Java 11 (Java Development Kit - JDK: 11.0.9)**
* **Spring Boot 2.3.7**
* **Maven**
* **JUnit 5**
* **PostgreSQL 13**
* **Swagger 3.0.0**
* **Model Mapper 2.3.9**
* **Travis CI**
* **Heroku**


# Como Rodar?

* Para executar o projeto é necessário que se tenha o docker instalado.
* Após a instalação, rode os seguintes comandos:

```bash
docker-compose build --force-rm
docker-compose up -d
```
* Para acesso da documentação com Swagger: http://localhost:8080/swagger-ui/index.html

Outra alternativa é rodar o projeto a partir do jar:
```bash
java -jar customer-management-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
```
ou

```bash
mvn spring-boot:run -Dspring.profiles.active=dev
```
Para isso é necessário que se tenha o java 11 instalado, assim como o Postgres. Deve ser adicionado o usuário pgteste:
```bash
sudo -u postgres createuser -s -i -d -r -l -w pgteste
```
```bash
sudo -u postgres psql -c "ALTER ROLE pgteste WITH PASSWORD 'pgteste';"
```

Depois disso deve ser criado o banco:

```bash
create database customer;
```

### Test

* Para os testes unitários

```bash
mvn test
```

* Para testes de integração

```bash
mvn integration-test
```

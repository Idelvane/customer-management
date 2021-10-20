# Customer management

A aplicação tem o intuito de manipulação de dados de um cliente. Para isso é necessário que seja possível criar, editar, listar e atualizar os dados por completo e também de forma granular.

# Requisitos necessários:

* Permita criação de novos clientes;

* Permita a atualização de clientes existentes;

* Permita que seja possível listar os clientes de forma paginada;

* Permita que buscas por atributos cadastrais do cliente;

* É necessário também que cada elemento retornado pela API de clientes informe a idade;

# Endpoints

* Criar usuário: POST /customer-management/v1/users
**Body:**
```json
{
  "email": "antonio.teste@teste.com",
  "name": "Antonio",
  "password": "159159",
  "role": "ADMIN"
}
```
**Retorno**
```json
{
    "data": {
        "id": 1,
        "name": "Antonio",
        "password": "$2a$10$bkcEIVoqfF1RgoSj8pRJO.IODL6RJXhC29tKSajvGXLFie2CL/ALG",
        "email": "antonio.teste@teste.com",
        "role": "ADMIN",
        "links": [
            {
                "rel": "self",
                "href": "http://localhost:8081/customer-management/v1/users/1"
            }
        ]
    }
}
```
* Autenticar (para utilização dos demais endpoints): POST /customer-management/v1/auth
**Body:**
```json
{
  "email": "antonio.teste@teste.com",
  "password": "159159"
}
 
```
**Retorno**
```json
{
    "data": {
        "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbnRvbmlvLnRlc3RlQHRlc3RlLmNvbSIsInJvbGUiOiJBRE1JTiIsImNyZWF0ZWQiOjE2MzQ2OTM2MDg3OTUsImV4cCI6MTYzNDY5NzIwOH0.f3r0cRP-Pc3tkRYlMf0fP3PDM2veWUsoXeEfyPhBGGEMnNbhmJtmOTRaz40mWg1hrdEsBIrR_7Y3WplNhQYpDA"
    }
}
```
**Para os demais endpoints é necessário que se realize a autenticação prévia e seja passado o token como parâmetro: Bearer token**

* Criar um cliente: POST /customer-management/v1/customers
**Body:**

```json
{
   "name": "Antonio",
   "document": "26197769026",
   "email": "email.idelvane@teste.com",
   "phone": "(86) 9999-9900",
   "personType": "FISICA",
   "birthDate": "1986-05-21T07:40:51.312",
   "createdAt": "2021-10-13T14:22:00.500",
   "updatedAt": "2021-10-13T14:22:00.100"
}
```
**Retorno**
```json
{
    "data": {
        "id": 1,
        "name": "Antonio",
        "document": "26197769026",
        "email": "email.idelvane@teste.com",
        "phone": "(86) 9999-9900",
        "birthDate": "1986-05-21T07:40:51.312",
        "createdAt": "2021-10-13T14:22:00.500",
        "updatedAt": "2021-10-13T14:22:00.100",
        "age": 35,
        "links": [
            {
                "rel": "self",
                "href": "http://localhost:8081/customer-management/v1/customers/1"
            }
        ]
    }
}
```

* Atualizar um cliente: PUT /customer-management/v1/customers

**Body:**

```json
{
   "id": 1,
   "name": "Antonio Idelvane",
   "document": "01788561341",
   "email": "email.idelvane@teste.com",
   "phone": "(86) 9999-9801",
   "personType": "FISICA",
   "birthDate": "1996-05-21T07:40:51.312",
   "createdAt": "2021-10-13T14:22:00.500",
   "updatedAt": "2021-10-13T14:22:00.100"
}
```
**Retorno**
```json
{
    "data": {
        "id": 1,
        "name": "Antonio Idelvane",
        "document": "01788561341",
        "email": "email.idelvane@teste.com",
        "phone": "(86) 9999-9801",
        "personType": "FISICA",
        "age": 25,
        "birthDate": "1996-05-21T07:40:51.312",
        "createdAt": "2021-10-13T14:22:00.500",
        "updatedAt": "2021-10-19T22:33:42.479456",
        "links": [
            {
                "rel": "self",
                "href": "http://localhost:8081/customer-management/v1/customers/1"
            }
        ]
    }
}
```


* Deletar um cliente (pelo id): DELETE /customer-management/v1/customers/1

**Retorno**
```json
{
    "data": "Customer com id 1, deletado com sucesso"
}
```
* Encontrar um cliente pelo ID: GET /customer-management/v1/customers/1

**Retorno**
```json

{
    "data": {
        "id": 1,
        "name": "Antonio Idelvane",
        "document": "01788561341",
        "email": "email.idelvane@teste.com",
        "phone": "(86) 9999-9801",
        "personType": "FISICA",
        "age": 25,
        "birthDate": "1996-05-21T07:40:51.312",
        "createdAt": "2021-10-13T14:22:00.500",
        "updatedAt": "2021-10-19T22:33:42.479456",
        "links": [
            {
                "rel": "self",
                "href": "http://localhost:8081/customer-management/v1/customers/1"
            }
        ]
    }
}
```
* Filtrar cliente por nome: GET /customer-management/v1/customers/byName/{name}
**Retorno semelhante ao endpoint GET /customer-management/v1/customers/1**

* Filtrar cliente por documento: GET /customer-management/v1/customers/byDocument/{document}
**Retorno semelhante ao endpoint GET /customer-management/v1/customers/1**

* Filtrar cliente pelo e-amil: GET /customer-management/v1/customers/byEmail/{email}
**Retorno semelhante ao endpoint GET /customer-management/v1/customers/1**

* Buscar clientes pela data de cadastro (ordenados e paginados): GET /customer-management/v1/customers?startDate=2021-10-10&endDate=2021-10-20&page=1&size=5&sort=id,DESC
**Retorno**
```json
{
    "data": [
        {
            "id": 1,
            "name": "Antonio Idelvane",
            "document": "01788561341",
            "email": "email.idelvane@teste.com",
            "phone": "(86) 9999-9801",
            "personType": "FISICA",
            "age": 25,
            "birthDate": "1996-05-21T07:40:51.312",
            "createdAt": "2021-10-13T14:22:00.500",
            "updatedAt": "2021-10-19T22:33:42.479456",
            "links": [
                {
                    "rel": "self",
                    "href": "http://localhost:8081/customer-management/v1/customers/1"
                }
            ]
        },
        {
            "id": 2,
            "name": "Maria",
            "document": "010.010.015-01",
            "email": "email.da.maria@teste.com",
            "phone": "(86) 9999-9902",
            "personType": "FISICA",
            "age": 31,
            "birthDate": "1990-06-20T07:40:51.312",
            "createdAt": "2021-10-12T14:22:00.500",
            "updatedAt": "2021-10-12T14:23:00.100",
            "links": [
                {
                    "rel": "self",
                    "href": "http://localhost:8081/customer-management/v1/customers/2"
                }
            ]
        }
    ]
}
```

# Tecnologias utilizadas

* **Java 11 (Java Development Kit - JDK: 11.0.9)**
* **Spring Boot 2.3.7**
* **Spring Security**
* **JWT**
* **Maven**
* **JUnit 5**
* **PostgreSQL 13**
* **Swagger 3.0.0**
* **Model Mapper 2.3.9**
* **Travis CI**
* **Heroku**


# Como Rodar?

* Para executar o projeto, existem duas possibilidades:

**Docker**

Para isso, é necessário que se tenha o docker instalado.

* Após a instalação, rode os seguintes comandos:

```bash
docker-compose build --force-rm
docker-compose up -d
```
* Para acesso da documentação com Swagger: http://localhost:8080/swagger-ui/index.html

**Executando o JAR**

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
### Heroku

Acesso da documentação com Swagger no Heroku: https://api-customer-management-test.herokuapp.com/swagger-ui/index.html

### Test

* Para os testes unitários

```bash
mvn test
```

* Para testes de integração

```bash
mvn integration-test
```

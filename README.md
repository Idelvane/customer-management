# Manage customers

A aplicação tem o intuito de manipulação de dados de um cliente. Para isso é necessário que seja possível criar, editar, listar e atualizar os dados por completo e também de forma granular.

# Requisitos necessários:

* Permita criação de novos clientes;

* Permita a atualização de clientes existentes;

* Permita que seja possível listar os clientes de forma paginada;

* Permita que buscas por atributos cadastrais do cliente;

* É necessário também que cada elemento retornado pela API de clientes informe a idade;

# Endpoints

Os principais endpoints da API são:

* Criar um clinte: POST /manage-customers/v1/customers
* Atualizar um cliente: PUT /manage-customers/v1/customers
* Deletar um cliente (pelo id): DELETE /manage-customers/v1/customers/1
* Encontrar um cliente pelo ID: GET /manage-customers/v1/customers/1
* Filtrar cliente por nome: GET /manage-customers/v1/customers/byName/{name}
* Filtrar cliente por documento: GET /manage-customers/v1/customers/byDocument/{name}
* Filtrar cliente por data de nascimento: GET /manage-customers/v1/customers/byBirthDate/{birthDate}
* Buscar clientes pela data de cadastro (ordenados e paginados): GET /manage-customers/v1/customers?startDate=2021-10-01&endDate=2021-10-15&page=3&size=5&sort=DESC

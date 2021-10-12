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

# Requisitos do módulo de Services - V1

## Objetivo do módulo

Permitir o cadastro e a manutenção dos **services** (serviços) oferecidos pela barbearia, com informações básicas e regras simples de disponibilidade para uso nos registros de **attendances**.

## Escopo desta parte

Nesta etapa, o sistema deve permitir:

- cadastrar um **service**;
- listar **services** cadastrados;
- buscar um **service** por ID;
- editar um **service**;
- inativar um **service**.

## Estado de implementação

### Implementado

- Entidade `ProvidedService` com persistência JPA;
- `ProvidedServiceRepository` para acesso aos dados e consultas de duplicidade;
- `ServiceRequestDto` e `ServiceResponseDto` para entrada e saída da API;
- `ServiceManager` com casos de uso de cadastro, listagem, busca por ID, edição e inativação, incluindo mapeamento entre entidade e DTOs;
- `ServiceController` expondo endpoints REST em `/services`;
- Validação de entrada com Bean Validation;
- Tratamento global de erros com `GlobalExceptionHandler` e `ApiErrorResponse(statusCode, message)`;
- Exceções de negócio: `ActiveNameAlreadyExistsException` (409) e `ServiceNotFoundException` (404).

### Endpoints

- `POST /services` — cria um service (`201 Created`)
- `GET /services` — lista services (`200 OK`)
- `GET /services/{id}` — busca service por ID (`200 OK` / `404`)
- `PUT /services/{id}` — atualiza um service (`200 OK`)
- `PATCH /services/{id}/deactivate` — inativa um service (`200 OK`)

### Ainda pendente nesta parte

- Nenhum item funcional pendente para o escopo V1 de Services.
- Testes automatizados podem ser expandidos/refatorados conforme a evolução do projeto.

## Entidade `ProvidedService`

Campos principais:

- `id` (gerado pelo sistema);
- `name` (obrigatório);
- `suggestedPrice` (BigDecimal);
- `active` (booleano, soft-delete).

## Regras de negócio confirmadas

- Todo service novo deve ser criado com `active = true`.
- O **name** do service é obrigatório.
- Não deve haver duplicação de **name** entre services ativos (case-insensitive).
- Um novo service pode reutilizar o nome de um service inativo.
- O **suggestedPrice** e o **name** podem ser alterados posteriormente.
- Services inativos **não** devem ser utilizados em novos registros de **attendance**.
- Um service inativo não precisa ser excluído fisicamente do banco de dados.
- A edição é permitida para services ativos e inativos, sem alterar o campo `active` via `PUT`.
- Só existe inativação na V1, sem reativação e sem exclusão física.
- A listagem retorna ativos e inativos juntos na V1, sem filtro por status.
- Combos podem ser cadastrados como services comuns, sem lógica especial nesta etapa.

## Catálogo inicial levantado

### Services já mencionados

- Degradê / Social;
- Beard;
- Combo;
- Platinum;
- Highlights;
- Relaxamento;
- Depilação nasal e auricular;
- Pedicure;
- Sobrancelha;
- Freestyle.

### Services sugeridos para versões futuras

- Barba terapia;
- Limpeza de pele.

## Fora do escopo desta parte

- Registro de **attendances** (já implementado em módulo separado);
- Indicadores;
- Fluxo de caixa;
- Produtos;
- Estoque;
- Assinantes;
- Categorias complexas de service;
- Integrações externas.

## Dúvidas em aberto

- Os services precisarão de categorias em versões futuras?
- O preço zero deve ser tratado como service gratuito ou cortesia, ou apenas como um valor provisório permitido até a validação com o responsável pela barbearia?
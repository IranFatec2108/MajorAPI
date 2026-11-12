# Requisitos do módulo de Services - V1

## Objetivo do módulo

Permitir o cadastro e a manutenção dos **services** oferecidos pela barbearia, com informações básicas e regras simples de disponibilidade para uso futuro nos registros de **attendance**.

## Escopo desta primeira parte

Nesta etapa, o sistema deve permitir:

- cadastrar um **service**;
- listar **services** cadastrados;
- editar um **service**;
- inativar um **service**.

## Estado de implementação da parte

### Implementado até o momento

- entidade `Service`;
- persistência por meio do `ServiceRepository`;
- `ServiceRequestDto` e `ServiceResponseDto`;
- caso de uso de cadastro por meio do `ServiceService`;
- caso de uso de listagem por meio do `ServiceService`;
- caso de uso de busca por id por meio do `ServiceService` com `findById` e `Optional`;
- caso de uso de edição por meio do `ServiceService` com atualização de **name** e **suggestedPrice** na entidade existente;
- caso de uso de inativação lógica por meio do `ServiceService` com `findById`, `Optional` e marcação de `active = false` sem exclusão física;
- endpoint REST para cadastro de service;
- endpoint REST `GET /services` para listagem de services;
- endpoint REST `GET /services/{id}` para busca por id com `@PathVariable`;
- endpoint REST `PUT /services/{id}` para edição com DTO completo e `@PathVariable`;
- endpoint REST `PATCH /services/{id}/deactivate` para inativação lógica só com id na URL, sem corpo;
- consulta `existsByNameIgnoreCaseAndActiveTrueAndIdNot` para duplicidade ignorando o próprio service;
- validação da entrada com Bean Validation;
- retorno HTTP `201 Created` em caso de cadastro bem‑sucedido;
- tratamento global de erros com `GlobalExceptionHandler` e `ApiErrorResponse(statusCode, message)`;
- duplicidade de nome entre services ativos retorna `409 Conflict`;
- dados inválidos no DTO retornam `400 Bad Request` com as mensagens juntas;
- exceção de negócio `ActiveNameAlreadyExistsException` como `RuntimeException`, sem `throws` no service e no controller;
- busca por id inexistente retorna `404 Not Found` por meio de `ServiceNotFoundException` no formato `ApiErrorResponse`.

### Ainda pendente nesta parte

- Nenhum item pendente. Módulo de **Services** fechado na V1 com cadastro, listagem, busca por id, edição e inativação.

## Entidade `Service`

Campos previstos:

- `id`;
- `name`;
- `suggestedPrice`;
- `active`.

## Regras de negócio confirmadas

- O `id` deve ser gerado pelo sistema.
- Todo service novo deve ser criado com `active = true`.
- O **name** do service é obrigatório.
- Não deve haver duplicação de **name** entre services ativos.
- Um novo service pode reutilizar o nome de um service inativo.
- O **suggestedPrice** pode ser alterado posteriormente.
- O **name** do service também pode ser alterado posteriormente.
- Services inativos **não** devem ser utilizados em novos registros de **attendance**.
- Um service inativo não precisa ser excluído fisicamente do banco de dados.
- A edição é permitida para services ativos e inativos, sem alterar o campo `active`.
- Só existe inativação na V1, sem reativação e sem exclusão física.
- Lista retorna ativos e inativos juntos na V1, sem filtro `?active`.
- Combos podem ser cadastrados como services comuns, sem necessidade de lógica especial nesta etapa.

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

- Registro de **attendances**;
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
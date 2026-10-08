# Requisitos do módulo de Serviços - V1

## Objetivo do módulo

Permitir o cadastro e a manutenção dos serviços oferecidos pela barbearia, com informações básicas e regras simples de disponibilidade para uso futuro nos registros de atendimento.

## Escopo desta primeira parte

Nesta etapa, o sistema deve permitir:

- cadastrar um serviço;
- listar serviços cadastrados;
- editar um serviço;
- inativar um serviço.

## Estado de implementação da parte

### Implementado até o momento

- entidade `Servico`;
- persistência por meio do `ServicoRepository`;
- `ServicoRequestDto` e `ServicoResponseDto`;
- caso de uso de cadastro por meio do `ServicoService`;
- caso de uso de listagem por meio do `ServicoService`;
- caso de uso de busca por id por meio do `ServicoService` com `findById` e `Optional`;
- endpoint REST para cadastro de serviço;
- endpoint REST `GET /servicos` para listagem de serviços;
- endpoint REST `GET /servicos/{id}` para busca por id com `@PathVariable`;
- validação da entrada com Bean Validation;
- retorno HTTP `201 Created` em caso de cadastro bem-sucedido;
- tratamento global de erros com `GlobalExceptionHandler` e `ApiErrorResponse(statusCode, message)`;
- duplicidade de nome entre ativos retorna `409 Conflict`;
- dados inválidos no DTO retornam `400 Bad Request` com as mensagens juntas;
- exceção de negócio `NomeAtivoJaExistenteException` como `RuntimeException`, sem `throws` no service e no controller;
- busca por id inexistente retorna `404 Not Found` por meio de `ServicoNaoEncontradoException` no formato `ApiErrorResponse`.

### Ainda pendente nesta parte

- edição de serviço;
- inativação de serviço.

## Entidade `Servico`

Campos previstos:

- `id`;
- `nome`;
- `precoSugerido`;
- `ativo`.

## Regras de negócio confirmadas

- O `id` deve ser gerado pelo sistema.
- Todo serviço novo deve ser criado com `ativo = true`.
- O nome do serviço é obrigatório.
- Não deve haver duplicação de nome entre serviços ativos.
- Um novo serviço pode reutilizar o nome de um serviço inativo.
- O preço sugerido pode ser alterado posteriormente.
- O nome do serviço também pode ser alterado posteriormente.
- Serviços inativos não devem ser utilizados em novos registros de atendimento.
- Um serviço inativo não precisa ser excluído fisicamente do banco de dados.
- Combos podem ser cadastrados como serviços comuns, sem necessidade de lógica especial nesta etapa.

## Catálogo inicial levantado

Serviços já mencionados:

- Degradê/social;
- Barba;
- Combo;
- Platinado;
- Luzes;
- Relaxamento;
- Depilação nasal e auricular;
- Pezinho;
- Sobrancelha;
- Freestyle.

Serviços mencionados como possibilidades futuras:

- Barba terapia;
- Limpeza de pele.

## Fora do escopo desta parte

- registro de atendimentos;
- indicadores;
- fluxo de caixa;
- produtos;
- estoque;
- assinantes;
- categorias complexas de serviço;
- integrações externas.

## Dúvidas em aberto

- Será necessário filtrar serviços ativos e inativos já na primeira versão?
- Os serviços precisarão de categorias em versões futuras?
- O preço zero deve ser tratado como serviço gratuito ou cortesia, ou apenas como um valor provisório permitido até a validação com o responsável pela barbearia?
# Barbearia Control API

Projeto de portfólio backend em Java e Spring Boot para uma barbearia real, com foco no aprendizado de modelagem de domínio, regras de negócio, persistência e construção de uma API REST a partir de um caso de uso concreto.

A V1 foi planejada para ser simples, incremental e honesta quanto ao seu escopo e às suas limitações.

## Objetivo

Construir uma API para apoiar o controle interno da barbearia, começando pelo cadastro de serviços e evoluindo futuramente para o registro de atendimentos, controle financeiro simples, estoque básico e assinaturas, caso ainda façam sentido para o negócio.

## Escopo da V1

A V1 está sendo desenvolvida em partes, começando pelo módulo de Serviços.

A ordem de implementação planejada é:

1. Serviços
2. Registro de atendimentos
3. Consultas de atendimentos
4. Entradas e saídas
5. Indicadores financeiros
6. Produtos e estoque
7. Assinantes, caso ainda façam sentido para o negócio

## Estado atual

Até o momento, foram concluídas as primeiras partes técnicas do módulo de Serviços:

- entidade `Servico` configurada para persistência com JPA;
- `ServicoRepository` para acesso aos dados;
- `ServicoRequestDto` e `ServicoResponseDto` para entrada e saída da API;
- `ServicoService` com os casos de uso de cadastro, listagem, busca por id, edição e inativação e mapeamento entre entidade e DTO;
- `ServicoController` com endpoints REST para cadastro, listagem, busca por id, edição e inativação de serviços;
- `GlobalExceptionHandler` com `@RestControllerAdvice` e `ApiErrorResponse(statusCode, message)` para respostas de erro padronizadas;
- `NomeAtivoJaExistenteException` como exceção de negócio não checada (`RuntimeException`);
- `ServicoNaoEncontradoException` como exceção de negócio não checada para id inexistente.

O fluxo atual de cadastro recebe os dados pela API REST, valida o DTO de entrada, verifica duplicidade de nome entre serviços ativos, define novos serviços como ativos por padrão, persiste a entidade pelo repository e retorna `201 Created` com um DTO de resposta.

Respostas atuais do cadastro:

- `201 Created`: serviço criado, retorna o DTO de resposta;
- `400 Bad Request`: dados inválidos no DTO, retorna as mensagens de validação juntas no formato `ApiErrorResponse`;
- `409 Conflict`: nome já existe em um serviço ativo, retorna a mensagem da regra de negócio no formato `ApiErrorResponse`.

Listagem atual:

- `GET /servicos` retorna `200 OK` com a lista de `ServicoResponseDto`, mesmo que vazia (`[]`).
- `GET /servicos/{id}` retorna `200 OK` com o `ServicoResponseDto` quando existe e `404 Not Found` no formato `ApiErrorResponse` quando não existe.

Edição atual:

- `PUT /servicos/{id}` recebe o `ServicoRequestDto` completo e retorna `200 OK` com o `ServicoResponseDto` atualizado.
- A edição exige `nome` e `precoSugerido` juntos, mesmo quando só um campo mudou, e não altera o campo `ativo`.
- A verificação de duplicidade ignora o próprio serviço, então manter o mesmo nome não gera conflito.
- `400 Bad Request`: dados inválidos no DTO, no formato `ApiErrorResponse`.
- `404 Not Found`: id inexistente, no formato `ApiErrorResponse`.
- `409 Conflict`: nome já existe em outro serviço ativo, no formato `ApiErrorResponse`.

Inativação atual:

- `PATCH /servicos/{id}/inativar` recebe só o id na URL, sem corpo, e retorna `200 OK` com o `ServicoResponseDto` com `ativo=false`.
- A inativação é lógica, sem exclusão física, e o inativo continua no banco para histórico.
- Inativar algo já inativo mantém inativo, sem erro novo.
- Um novo serviço pode reutilizar o nome de um serviço inativo.
- `404 Not Found`: id inexistente, no formato `ApiErrorResponse`.

As decisões iniciais e dúvidas ainda abertas continuam registradas na pasta `docs/`.

## Módulos previstos

- Serviços
- Atendimentos
- Entradas e saídas
- Produtos e estoque básico
- Assinantes
- Indicadores simples

## Limites da V1

Não fazem parte da primeira versão:

- agendamento online;
- automação de WhatsApp;
- integrações financeiras;
- pagamentos;
- aplicativo mobile;
- gráficos avançados;
- autenticação avançada sem uma nova decisão explícita.

## Documentação

- `docs/requisitos-servicos-v1.md`: requisitos e anotações de implementação do primeiro módulo.
- `docs/decisoes-em-aberto.md`: decisões importantes que ainda precisam ser validadas.

## Próximos passos

1. Implementar o registro de atendimentos.
2. Manter o README e os documentos atualizados a cada nova parte concluída.
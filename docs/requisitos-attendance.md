# Requisitos do módulo **Attendance** – V1

## Objetivo
Registrar cada atendimento realizado na barbearia, preservando:
- O serviço utilizado no momento (snapshot do nome);
- O valor pago pelo cliente;
- A data/hora da realização;
- Um status lógico (`valid`) que permite soft-delete e reativação.

## Estado de implementação

- ✅ Entidade `Attendance` com persistência JPA
- ✅ `AttendanceRepository` com consultas para listagem de válidos/inválidos
- ✅ DTOs: `AttendanceCreateRequestDto`, `AttendanceUpdateRequestDto`, `AttendanceResponseDto`
- ✅ `AttendanceManager` com regras de negócio e mapeamentos
- ✅ `AttendanceController` com endpoints REST em `/api/attendances`
- ✅ Tratamento de erros integrado ao `GlobalExceptionHandler`
- ⏳ Testes automatizados específicos do módulo (pendente)
- 📋 Filtros avançados e paginação (decisões/implementação futuras)

## Escopo implementado (V1)

1. **Cadastro de atendimento**
   - **Endpoint:** `POST /api/attendances`
   - **Request DTO:** `AttendanceCreateRequestDto`
     - `providedServiceId` (Long) — **obrigatório**, deve referenciar um `ProvidedService` **ativo**.
     - `amountPaid` (BigDecimal) — **obrigatório**, `>= 0`, até 2 casas decimais.
   - **Validações:**
     - Bean Validation (`@NotNull`, `@PositiveOrZero`, `@Digits`).
     - No manager, busca o serviço ativo; se não existir, lança exceção de negócio tratada como `404`.
   - **Processo interno:**
     - Cria entidade `Attendance` com:
       - `providedService` (referência JPA)
       - `serviceNameSnapshot` (cópia do nome do serviço no momento do registro)
       - `amountPaid`
       - `dateTime = LocalDateTime.now()`
       - `valid = true`
     - Persiste via `AttendanceRepository.save`.
   - **Resposta:** `201 Created` com `AttendanceResponseDto` (`id`, `serviceNameSnapshot`, `amountPaid`, `dateTime`, `valid`).

2. **Listagem de atendimentos**
   - **Endpoint:** `GET /api/attendances`
   - **Query param (opcional):** `valid=true|false`
   - **Comportamento:** se omitido (ou `true`), retorna atendimentos válidos; se `false`, retorna inválidos.
   - **Retorno:** `200 OK` com lista de `AttendanceResponseDto` ordenada por `dateTime DESC`.

3. **Busca por ID**
   - **Endpoint:** `GET /api/attendances/{id}`
   - **Retorno:** `200 OK` com `AttendanceResponseDto` ou `404` (`AttendanceNotFoundException`).

4. **Atualização parcial**
   - **Endpoint:** `PATCH /api/attendances/{id}`
   - **Request DTO:** `AttendanceUpdateRequestDto` (campos opcionais)
     - `providedServiceId` (Long) — se presente, deve ser um serviço **ativo**.
     - `amountPaid` (BigDecimal) — se presente, `>= 0` (até 2 casas decimais).
   - **Regra:** altera apenas os campos enviados; os demais permanecem inalterados.
   - **Retorno:** `200 OK` com `AttendanceResponseDto` atualizado.

5. **Soft-delete (inativação)**
   - **Endpoint:** `PATCH /api/attendances/{id}/deactivate`
   - **Comportamento:** define `valid = false`. Se já estiver inativo, retorna o registro atual.
   - **Retorno:** `200 OK` com `AttendanceResponseDto` (`valid=false`).

6. **Reativação**
   - **Endpoint:** `PATCH /api/attendances/{id}/reactivate`
   - **Comportamento:** define `valid = true`. Se já estiver ativo, retorna o registro atual.
   - **Retorno:** `200 OK` com `AttendanceResponseDto` (`valid=true`).

## Regras de negócio confirmadas

| Regra | Onde está implementada |
|---|---|
| Só serviços **ativos** podem ser associados a um novo atendimento. | `AttendanceManager` (busca de serviço ativo ao registrar/atualizar). |
| O **snapshot** do nome do serviço é copiado no momento do registro e preserva o histórico. | Mapeamento/criação da entidade no manager. |
| **Soft-delete** (`valid`) mantém o registro no histórico e permite reativação. | `deactivateAttendance` / `reactivateAttendance`. |
| `amountPaid` não pode ser negativo e tem precisão de 2 casas decimais. | Validações nos DTOs (`@PositiveOrZero`, `@Digits`). |
| `dateTime` é definido na criação e não é editável. | Definição no momento da criação da entidade. |

## Padrões de resposta de erro (via `GlobalExceptionHandler`)

- **400 Bad Request** — falha de validação nos DTOs.
- **404 Not Found** — serviço/atendimento não encontrado.
- **409 Conflict** — não aplicável atualmente no fluxo principal de Attendance (reservado para futuras regras de conflito).

## Próximos passos

1. Implementar testes unitários e de integração para os endpoints de Attendance.
2. Definir e implementar filtros avançados (data, serviço, faixa de valor) e paginação, conforme decisões de produto.
3. Revisar impactos de decisões em aberto (pagamento, fluxo de caixa, cortesias) antes de estender o modelo.

---

**Observação:** Decisões pendentes que podem impactar este módulo estão em [`decisoes-em-aberto.md`](decisoes-em-aberto.md). 
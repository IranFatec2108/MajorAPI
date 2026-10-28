# Requisitos do módulo **Attendance** – V1

## Objetivo
Registrar cada atendimento realizado na barbearia, preservando:
- O serviço utilizado no momento (snapshot do nome);
- O valor pago pelo cliente;
- A data/hora da realização;
- Um status lógico (`valid`) que permite soft‑delete.

## Escopo da primeira entrega (V1)

1. **Cadastro de atendimento**
    - **Endpoint**: `POST /api/attendances`
    - **Request DTO**: `AttendanceCreateRequestDto`
        - `providedServiceId` (Long) – **obrigatório**, deve referenciar um `ProvidedService` **ativo**.
        - `amountPaid` (BigDecimal) – **obrigatório**, `≥ 0`, até 2 casas decimais.
    - **Validações**:
        - Bean‑validation (`@NotNull`, `@Positive`, `@PositiveOrZero`, `@Digits`).
        - No service, busca o serviço ativo; se não existir, lança `ServiceNotFoundException` → `404`.
    - **Processo interno**:
        - Cria entidade `Attendance` com:
            - `providedService` (referência JPA)
            - `serviceNameSnapshot` (cópia do nome do serviço)
            - `amountPaid`
            - `dateTime = LocalDateTime.now()`
            - `valid = true` (valor padrão)
        - Persiste via `AttendanceRepository.save`.
    - **Resposta**: `AttendanceResponseDto` contendo `id`, `serviceNameSnapshot`, `amountPaid`, `dateTime`, `valid`.

2. **Listagem de atendimentos**
    - **Endpoint**: `GET /api/attendances`
    - **Query param (opcional)**: `valid=true|false` (se não houver, retorna apenas os válidos).
    - **Retorno**: lista de `AttendanceResponseDto` ordenada por `dateTime DESC`.

3. **Busca por ID**
    - **Endpoint**: `GET /api/attendances/{id}`
    - **Retorno**: `AttendanceResponseDto` ou `404` (`AttendanceNotFoundException`).

4. **Atualização parcial**
    - **Endpoint**: `PATCH /api/attendances/{id}`
    - **Request DTO**: `AttendanceUpdateRequestDto` (campos opcionais)
        - `providedServiceId` (Long) – se presente, deve ser um serviço **ativo**.
        - `amountPaid` (BigDecimal) – se presente, `≥ 0` (até 2 casas decimais).
    - **Regra**: só altera os campos enviados; os demais permanecem como estavam.
    - **Retorno**: `AttendanceResponseDto` atualizado.

5. **Soft‑delete (inativação)**
    - **Endpoint**: `PATCH /api/attendances/{id}/deactivate`
    - **Comportamento**: seta `valid = false`. Se já estiver `false`, devolve o mesmo registro.
    - **Retorno**: `AttendanceResponseDto` com `valid = false`.

6. **Reativação**
    - **Endpoint**: `PATCH /api/attendances/{id}/reactivate`
    - **Comportamento**: seta `valid = true`. Se já estiver `true`, devolve o mesmo registro.
    - **Retorno**: `AttendanceResponseDto` com `valid = true`.

## Regras de negócio confirmadas

| Regra | Onde está implementada |
|-------|------------------------|
| Só serviços **ativos** podem ser associados a um novo atendimento. | `AttendanceManager.registerAttendance` (busca via `findByIdAndActiveTrue`). |
| O **snapshot** do nome do serviço é copiado na hora do cadastro e não muda automaticamente se o serviço for renomeado depois. | `AttendanceManager.toEntity` (seta `serviceNameSnapshot`). |
| **Soft‑delete** (`valid`) → registro permanece no histórico e pode ser reativado. | `deactivateAttendance`/`reactivateAttendance`. |
| `amountPaid` nunca pode ser negativo e tem precisão de 2 casas decimais. | Anotações `@PositiveOrZero` e `@Digits` nos DTOs. |
| `dateTime` é definido no momento da criação (não editável). | `AttendanceManager.toEntity` (`LocalDateTime.now()`). |

## Padrões de respostas (via `GlobalExceptionHandler`)

- **404 Not Found** – `ServiceNotFoundException` ou `AttendanceNotFoundException`.
- **400 Bad Request** – falha de bean‑validation nos DTOs.
- **409 Conflict** – não há conflito atualmente no módulo Attendance (poderá surgir se, no futuro, houver regra de unicidade de horário, por exemplo).

## Próximos passos (após a documentação)

1. Implementar `AttendanceController` seguindo exatamente os endpoints descritos.
2. (Opcional) Implementar testes unitários/integracionais para cada endpoint.
3. Atualizar a **Checklist** no `README.md` removendo o “⚙️” quando o controller estiver concluído.

---

**Observação:** Caso, futuramente, queira incluir **forma de pagamento**, **fluxo de caixa** ou **cortesias**, as decisões ainda em aberto estão listadas em `docs/open-decisions.md`. Elas devem ser revisadas antes de estender o modelo de `Attendance`. 
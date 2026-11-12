# Decisões em aberto da V1

## Serviços gratuitos ou cortesias

**Pergunta:** Quando o `amountPaid` for **0** (corte gratuito), ele deve:
1. Ser contabilizado apenas nos indicadores operacionais (ex.: número de atendimentos) **ou**
2. Também ser incluído nos indicadores financeiros (ex.: ticket médio)?

> **Ação:** validar essa política com o proprietário da barbearia. Até que a decisão seja tomada, o backend aceita `amountPaid = 0` sem restrição.

## Fluxo de caixa

**Decisão em aberto:**
- **Opção 1:** gerar automaticamente uma entrada de caixa ao registrar um atendimento (valor = `amountPaid`).
- **Opção 2:** registrar a entrada de caixa em um passo posterior, permitindo ajustes manuais.

> **Status:** ainda não implementado. A lógica de caixa será planejada depois que a camada de `AttendanceController` estiver estável, garantindo que os atendimentos estejam bem definidos.

## Forma de pagamento

**Decisão pendente:**
- **Opção A:** incluir um campo `paymentMethod` (enum: `CASH`, `CARD`, `PIX`, `OTHER`) no `AttendanceCreateRequestDto` já na V1.
- **Opção B:** adiar esse campo para a V2, mantendo apenas `amountPaid` por enquanto.

> **Ação:** discutir com o dono da barbearia quais formas de pagamento são utilizadas atualmente. Até que a decisão seja tomada, **não há campo `paymentMethod`** no modelo de `Attendance`.

## Produtos e estoque

Produtos e estoque básico fazem parte da visão maior da V1, mas entram depois do módulo de **Services** e da primeira parte de **Attendances**.

## Categorias de serviço

Ainda não foi decidido se os services precisarão de categorias, como corte, barba, combo ou estética.

## Indicadores

Indicadores como ticket médio, ranking de services e movimento por dia devem ser pensados depois que o sistema já tiver registro de **attendances** suficiente para consulta.

## Observação geral

Toda decisão ainda aberta deve ser validada antes da implementação para evitar regras inventadas e retrabalho.
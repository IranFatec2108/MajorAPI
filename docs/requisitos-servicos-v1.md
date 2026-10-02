# Requisitos do módulo de Serviços - V1

## Objetivo do módulo

Permitir o cadastro e a manutenção dos serviços oferecidos pela barbearia, com informações básicas e regras simples de disponibilidade para uso futuro nos registros de atendimento.

## Escopo desta primeira fatia

Nesta etapa, o sistema deve permitir:

- Cadastrar um serviço
- Listar serviços cadastrados
- Editar um serviço
- Inativar um serviço

## Estado de implementação da fatia

Implementado até o momento:
- entidade `Servico`;
- persistência via `ServicoRepository`;
- validações iniciais e cadastro via `ServicoService`.

Ainda pendente nesta fatia:
- exposição do cadastro por endpoint REST;
- listagem de serviços;
- edição de serviço;
- inativação de serviço;
- definição de DTOs e respostas HTTP.

## Entidade Servico

Campos previstos:

- `id`
- `nome`
- `precoSugerido`
- `ativo`

## Regras de negócio confirmadas

- O `id` deve ser gerado pelo sistema.
- Todo serviço novo deve ser criado com `ativo = true`.
- O nome do serviço deve ser obrigatório.
- Não deve haver duplicação de nome entre serviços ativos.
- Um novo serviço pode reutilizar o nome de um serviço inativo.
- O preço sugerido pode ser alterado depois.
- O nome do serviço também pode ser alterado depois.
- Serviços inativos não devem ser usados em novos registros de atendimento.
- Um serviço inativo não precisa ser excluído fisicamente do banco.
- Combos podem ser cadastrados como serviços próprios, sem necessidade de lógica especial nesta fase.

## Catálogo inicial levantado

Serviços já mencionados:

- Degradê/social
- Barba
- Combo
- Platinado
- Luzes
- Relaxamento
- Depilação nasal e auricular
- Pezinho
- Sobrancelha
- Frestyle

Serviços mencionados como possibilidade futura:

- Barba terapia
- Limpeza de pele

## Fora do escopo desta fatia

- Registro de atendimentos
- Indicadores
- Fluxo de caixa
- Produtos
- Estoque
- Assinantes
- Categorias complexas de serviço
- Integrações externas

## Dúvidas em aberto

- Será necessário filtrar serviços ativos e inativos já na primeira versão?
- Haverá categorias de serviço em versões futuras?
- Preço sugerido zero deve ser tratado como serviço gratuito/cortesia ou apenas como valor provisório permitido até validação com o dono?
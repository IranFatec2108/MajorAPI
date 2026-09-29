# Barbearia Control API

Projeto de portfólio backend em Java e Spring Boot para uma barbearia real, com foco em aprender modelagem de domínio, regras de negócio, persistência e construção de API REST a partir de um caso de uso concreto. A proposta da V1 é ser simples, incremental e honesta quanto ao escopo e às limitações.

## Objetivo

Construir uma API para apoiar o controle interno da barbearia, priorizando o cadastro de serviços e, em etapas futuras, o registro de atendimentos, movimento financeiro simples, estoque básico e assinaturas.

## Escopo da V1

A V1 foi pensada para evoluir em fatias, começando pelo módulo de Serviços. A ordem de implementação prevista é: Serviços, Registro de Atendimentos, Consultas de Atendimentos, Entradas e Saídas, Indicadores Financeiros, Produtos/Estoque e, por fim, Assinantes se ainda fizer sentido para o negócio.

## Estado atual

No momento, o projeto está na fase de levantamento de requisitos e definição da primeira fatia do módulo de Serviços. As decisões iniciais e dúvidas abertas estão registradas na pasta `docs/` para orientar a modelagem e evitar regras desalinhadas.

## Módulos previstos

- Serviços
- Atendimentos
- Entradas e saídas
- Produtos e estoque básico
- Assinantes
- Indicadores simples

## Limites da V1

Não fazem parte da primeira versão: agendamento online, automação de WhatsApp, integrações financeiras, pagamentos, aplicativo mobile, gráficos avançados e autenticação avançada sem nova decisão explícita.

## Documentação inicial

- `docs/requisitos-servicos-v1.md`: requisitos do primeiro módulo a ser implementado.
- `docs/decisoes-em-aberto.md`: decisões importantes ainda não fechadas.

## Próximos passos

1. Revisar e consolidar os requisitos do módulo de Serviços.
2. Criar a issue de modelagem da entidade `Servico`.
3. Implementar a primeira fatia técnica do projeto com base na documentação validada.
4. Atualizar o README conforme o projeto evoluir, registrando o que foi implementado, o que está simulado e o que ainda falta.
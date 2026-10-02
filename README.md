# Barbearia Control API

Projeto de portfólio backend em Java e Spring Boot para uma barbearia real, com foco em aprender modelagem de domínio, regras de negócio, persistência e construção de API REST a partir de um caso de uso concreto. A proposta da V1 é ser simples, incremental e honesta quanto ao escopo e às limitações.

## Objetivo

Construir uma API para apoiar o controle interno da barbearia, priorizando o cadastro de serviços e, em etapas futuras, o registro de atendimentos, movimento financeiro simples, estoque básico e assinaturas.

## Escopo da V1

A V1 foi pensada para evoluir em fatias, começando pelo módulo de Serviços. A ordem de implementação prevista é: Serviços, Registro de Atendimentos, Consultas de Atendimentos, Entradas e Saídas, Indicadores Financeiros, Produtos/Estoque e, por fim, Assinantes se ainda fizer sentido para o negócio.

## Estado atual

No momento, o projeto concluiu as primeiras fatias técnicas do módulo de Serviços:
- entidade `Servico` compatível com persistência JPA;
- `ServicoRepository` para acesso aos dados;
- exceptions específicas para validação de cadastro;
- `ServicoService` com o caso de uso de cadastro implementado.

O fluxo atual de cadastro valida objeto nulo, nome obrigatório e duplicidade entre serviços ativos, define novos serviços como ativos por padrão e persiste a entidade pelo repository. As decisões iniciais e dúvidas abertas continuam registradas na pasta `docs/`.

## Módulos previstos

- Serviços
- Atendimentos
- Entradas e saídas
- Produtos e estoque básico
- Assinantes
- Indicadores simples

## Limites da V1

Não fazem parte da primeira versão: agendamento online, automação de WhatsApp, integrações financeiras, pagamentos, aplicativo mobile, gráficos avançados e autenticação avançada sem nova decisão explícita.

## Documentação

- `docs/requisitos-servicos-v1.md`: requisitos do primeiro módulo.
- `docs/decisoes-em-aberto.md`: decisões importantes ainda não fechadas.

## Próximos passos

1. Expor o cadastro de serviços por endpoint REST.
2. Definir DTOs de entrada e saída do módulo de Serviços.
3. Tratar respostas HTTP para cenários de sucesso, validação e conflito.
4. Evoluir o módulo de forma incremental com listagem, edição e inativação.
5. Atualizar o README conforme novas fatias técnicas forem concluídas.
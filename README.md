# back-end-maven

**Sistema de Gerenciamento de Aluguéis – Wheels**

## 📋 Descrição do Projeto

O projeto propõe a evolução de um sistema já existente para controle de aluguéis de bicicletas da loja Wheels. A proposta é tornar o processo de gestão de clientes, bicicletas e históricos de aluguel mais eficiente e organizado, reduzindo falhas manuais e centralizando informações em um sistema integrado.

## 🚀 Principais Melhorias

* Substituição do armazenamento local por banco de dados relacional (SQLite).
* Desenvolvimento de interface gráfica web utilizando React, HTML5, CSS3 e JavaScript.
* Validação de campos de entrada para evitar inconsistências no cadastro.
* Cadastro rápido de novos clientes, bicicletas e contratos.
* Monitoramento de contratos ativos e finalizados.
* **Integração com a API ViaCEP** para preenchimento automático de endereço ao cadastrar um cliente.
* **Integração com a API do Mercado Pago** para realização de pagamentos reais via **Pix, cartões de crédito/débito ou boleto bancário**.

## 💻 Tecnologias Utilizadas

* Java 17 + Maven (backend)
* SQLite (persistência de dados)
* HTML5 + CSS3 + JavaScript + React (frontend)
* Integrações com APIs externas: ViaCEP e Mercado Pago
* UML para documentação técnica: Casos de Uso, Diagrama de Classes, Diagrama de Sequência, Diagrama de Pacotes, Dicionário de Dados e Matriz de Associação.

## Estrutura Geral do Sistema

* **Frontend:** Interface para cadastro, consulta de bicicletas disponíveis e registro de aluguéis.
* **Backend:** APIs em Java responsáveis por gerenciar os dados de clientes, bicicletas e contratos de aluguel.
* **Banco de Dados:** Estrutura em SQLite para armazenar clientes, bicicletas e histórico de locações.

## Status

**FINALIZADO POR HORA.**

## Requisitos Básicos para Execução

* JDK 17+

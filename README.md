## Sistema de Controle para Lanchonete
Este projeto foi desenvolvido para atender às necessidades de uma lanchonete que atualmente não possui um sistema para gerenciar pedidos, pagamentos, clientes e produtos. O objetivo principal é otimizar o gerenciamento de pedidos e, assim, garantir um bom atendimento aos clientes.

## Importância do Sistema
O sistema de controle é crucial para o crescimento e expansão da lanchonete, facilitando a prestação de serviços de qualidade. Sem ele, as chances de crescimento podem ser impactadas, dificultando a obtenção do sucesso esperado.

## Desenvolvimento do Projeto
O projeto começou com o levantamento e discussão dos requisitos e características da lanchonete. A equipe utilizou metodologias como Domain-Driven Design (DDD) para definir os fluxos de trabalho a serem implementados. Além disso, etapas como o Event Storming foram fundamentais para entender os elementos necessários e o fluxo de desenvolvimento a ser seguido.

## Tecnologias Utilizadas
- Java e Spring: O sistema foi desenvolvido em Java utilizando o framework Spring, gerenciado pelo Maven.
- Docker: Para a criação de contêineres que hospedam a aplicação.
- MySQL: Banco de dados utilizado para armazenar as informações.
- Minikube: Como uma ferramenta de gerenciamento de clusters kubernetes
- Kubernetes: Para gerenciar toda a infaestrutura da nossa aplicação
- Helm: Para gerenciamento e atualização da aplicação no kubernetes atuando como gerenciador de pacotes
- API Mercado Pago: para realização de pagamentos na aplicação

## Funcionalidades e Documentação
O projeto conta com endpoints desenvolvidos e disponíveis para consumo. Além disso, uma documentação abrangente foi criada com o Swagger, permitindo uma utilização mais rápida e eficiente das APIs.

## Requesitos
- Docker
- docker-compose
- minikube
- kuibernetes (kube_adm, kube_ctl)
- helm


## Configuração
Primeiro, faça um clone do projeto local.
Em segundo, é necessário criar um arquivo chamado `.env` na raiz do projeto e configurar as variáveis de ambiente como:
```
PORT=8080

DB_HOST=db
DB_PORT=3306
DB_USER=tech-user
DB_PASSWORD=tech-pass
DB_NAME=tech-challenge

KEY_MERCADO_PAGO=TOKEN_MERCADO_PAGO
```
## Instalação
Depois de configurar as variáveis de ambiente, você irá precisar rodar o comando a seguir para iniciar a aplicação
```
docker-compose up ## ou
docker compose up ## dependendo da configuração do docker
```

## Links essenciais
Documentação de endpoints:
```
http://localhost:8080/swagger-ui/index.html ## documentação Swagger da aplicação
```

## Escalabilidade HPA
O desenho contempla dois cenários sendo o primeiro um cenário em que o sistema não recebe muitas requisições. Já o segundo cenário há um aumento considerável no número de riquições fazendo com que o HPA faça um escalonamento dos PODs.

[![image](https://github.com/PosTechChallengeFIAP/tech-challenge/blob/main/docs/TechC.drawio.png)](/)

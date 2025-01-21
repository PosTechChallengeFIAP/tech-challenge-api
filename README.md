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


## Configuração Para Docker Compose
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
## Instalação Com Docker Compose
Depois de configurar as variáveis de ambiente, você irá precisar rodar o comando a seguir para iniciar a aplicação
```
docker-compose up ## ou
docker compose up ## dependendo da configuração do docker
```
## Instalação Com Helm
Na pasta do helm chart de integração 'devops/helm/tech-challenge-integration-chart' execute os seguintes comandos
```
helm dependency update .
helm package .
helm install -f values.yaml tech-challenge-application tech-challenge-0.1.0.tgz
```
Acompanhe o deploy da sua aplicação com os comandos do 'kubectl'. Você pode também listar as instalações que você fez com o comando
```
helm list
```
Para desinstalar a aplicação basta executar
```
helm uninstall tech-challenge-application
```
## Links essenciais
Documentação de endpoints:
```
http://localhost:8080/swagger-ui/index.html ## documentação Swagger da aplicação
```

## Escalabilidade HPA
O desenho contempla dois cenários sendo o primeiro um cenário em que o sistema não recebe muitas requisições. Já o segundo cenário há um aumento considerável no número de requisições fazendo com que o HPA faça um escalonamento dos PODs.

O autoscaling está habilitado por padrão, mas pode ser desabilitado em tech-challenge-app/values.yaml.

[![image](https://github.com/PosTechChallengeFIAP/tech-challenge/blob/main/docs/TechC.drawio.png)](/)

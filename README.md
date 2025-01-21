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

## Caso de Uso End-to-End

Observação importante: Para montar o body de requisições, utilize a documentação do
SWAGGER disponibilizada.

1. Para que o projeto possa ser executado de maneira correta primeiro devemos gerar a
estrutura de nossa lanchonete, que no caso serão os produtos. Para cadastrar novos
produtos basta utilizar o endpoint `http://localhost:8080/product` e realizar uma requisição
HTTP do tipo POST. Lembre-se de incluir o body seguindo a documentação.

2. Após gerar produtos, o fluxo de pedidos pode ser inicializado. Nesse caso realize uma
requisição `POST` para o endpoint `http://localhost:8080/Order` e armazene seu ID.

3. Nesse fluxo há a possibilidade do cliente realizar identificação ou não.

    1. Caso deseje cadastrar um cliente realize uma requisição `POST` ao endpoint
`http://localhost:8080/client` (lembre-se de incluir o body seguindo a documentação) e
armazene seu ID. Logo em seguida será necessário vincular ao pedido através do seguinte
endpoint com uma requisição `POST http://localhost:8080/order/{orderId}/client/{clientId}`.
    2. Caso não queira informar o cliente, basta seguir em frente.

4. Seguindo esses passos, já podemos adicionar itens ao nosso pedido. Para isso realize
uma requisição `POST` ao seguinte endpoint `http://localhost:8080/order/{orderId}/orderItem`.
É importante ressaltar que nesse caso o ID do pedido deve ser informado no endpoint da
requisição `{orderId}` e além disso deve haver o corpo da requisição informando o ID de
algum produto criado anteriormente.
Isso já é o suficiente para a estrutura básica, pois o pedido deve conter ao menos 1
item para que seja possível realizar o pagamento.

5. Nesse passo o pedido estará com o status de “ordering” que significa que o cliente ainda
está realizando seu pedido. Para prosseguir devemos realizar uma requisição `POST` no
endpoint `/order/:orderid/payment`, para atualizar o pedido para “payment pending” e criar um
pagamento (pagamento pendente) simulando a ação do cliente de clicar em “finalizar
pedido” na tela do totem.

6. Com o status de pagamento pendente é hora de realizar o pagamento através da url de
resposta do endpoint de criação de pagamento. paymentURL. Após clicar na URL, você
será redirecionado para a página de pagamentos do mercado pago.

7. Dentro da página de pagamentos do mercado pago, você precisará realizar o login com
as credenciais abaixo:  
__Usuário:__ TESTUSER1220079481  
__Senha:__ TD5F0oZom5  

9. Ao logar com o usuário teste, deverá escolher “Outro meio de pagamento” e, na próxima
tela, escolher cartão de crédito. Após isso, deverá preencher com as informações abaixo:  
__Numero:__ 5031 4332 1540 6351  
__Nome:__ APRO  
__Cod de Segurança:__ 123  
__Validade:__ 11/25  
__CPF:__ Gerar aleatoriamente  

10. Com as informações preenchidas, clique em continuar. Selecione qualquer parcela e
continue e finalize o pagamento.

11. Com o pagamento finalizado clique OBRIGATORIAMENTE no botão de ‘<- Voltar para a
página’

12. Internamente, o status do pedido estará informando que ele está na fila (QUEUE) e que
o pagamento foi finalizado (PAID). Assim, você pode conferir os pedidos da fila no endpoint
GET /queue

# 🛒 API de Sistema de Pedidos com Mensageria para Enviar E-mails
## ⚠️ Este repositório é um submódulo [deste repositório](https://github.com/lumahdev/shop-ms)

Neste projeto, realizei uma simulação de um microsserviço para o envio de e-mails baseado nos eventos comumente disparados em uma loja virtual.

<img width="1853" height="981" alt="image" src="https://github.com/user-attachments/assets/79903ba2-c8de-435d-9f0a-8633180f486f" />

## Tecnologias
- Java Spring
- Docker
- RabbitMQ
- Mongo

## Funcionalidades da API do monolito `orders`
- Usuário: cadastro, listar todos, listar específico, validar email;
- Produto: cadastro, listar todos, listar específico;
- Pagamento: realizar, listar todos, listar específico;
- Pedido: realizar, listar todos, listar específico, despachar, confirmar entrega.

## Eventos
- E-mail para validação de e-mail ao se cadastrar;
<img width="1653" height="644" alt="image" src="https://github.com/user-attachments/assets/13d95d3f-2681-4dea-b29d-f2476b44cb4b" />


- E-mail informando a conclusão do pedido e pendência do pagamento;
<img width="1665" height="811" alt="image" src="https://github.com/user-attachments/assets/bcaacb26-9474-4e6c-b9bb-f6e7662d7634" />


- E-mail informando a conclusão do pagamento e preparação do pedido;
<img width="1660" height="737" alt="image" src="https://github.com/user-attachments/assets/f36e8838-6d1e-4954-af9c-dee66d4bb41e" />


- E-mail informando que o pagamento não foi concluído;
<img width="1661" height="760" alt="image" src="https://github.com/user-attachments/assets/442935d4-75ea-47d0-913b-ac23ed6c319d" />


- E-mail informando que o pedido saiu para a entrega;
<img width="1660" height="737" alt="image" src="https://github.com/user-attachments/assets/b4843b82-6b2d-4f35-b85b-24f4478d6e5d" />

- E-mail informando que o pedido foi entregue.
<img width="1660" height="737" alt="image" src="https://github.com/user-attachments/assets/46ebd92b-0ef5-462e-9b06-e186658a2270" />


## Documentação da API
<img width="1920" height="963" alt="image" src="https://github.com/user-attachments/assets/9a6e4fd0-599e-4009-a986-367db02800b6" />
<img width="1920" height="963" alt="image" src="https://github.com/user-attachments/assets/4494a490-9bc5-4425-8d91-4513b100533f" />
<img width="1914" height="406" alt="image" src="https://github.com/user-attachments/assets/57d7d29f-b5f9-4817-a522-ff022ac9eee9" />

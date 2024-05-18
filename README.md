# API de Comércio Eletrônico
Esta API de comércio eletrônico oferece funcionalidades para gerenciar produtos, pedidos e usuários em uma plataforma de comércio eletrônico.
## Outros recursos
### Gerenciamento de Usuários Avançado 
- Além do registro e autenticação de usuários, a API suporta confirmação de email ao registrar
### Filtragem e Ordenação de Produtos 
- Os endpoints relacionados a produtos permitem filtrar e ordenar os resultados com base em critérios específicos, como preço, categoria ou avaliação.
### Envio e Rastreamento de Pedidos
- Funcionalidades para calcular custos de envio


## Endpoints
### Produtos (/api/products)
#### Listar Todos os Produtos
- GET /api/products: Retorna todos os produtos disponíveis na plataforma.
#### Buscar Produto por ID
-GET /api/products/{id}: Retorna os detalhes de um produto específico com o ID fornecido.
#### Adicionar Novo Produto
- POST /api/products: Adiciona um novo produto à plataforma.
Payload da Solicitação: Detalhes do produto a ser adicionado.
Resposta Bem-Sucedida: Detalhes do novo produto adicionado.
#### Atualizar Produto
- PUT /api/products/{id}: Atualiza um produto existente com base no ID fornecido.
- Payload da Solicitação: Detalhes atualizados do produto.
- Resposta Bem-Sucedida: Detalhes do produto atualizado.
#### Excluir Produto
- DELETE /api/products/{id}: Exclui um produto específico com base no ID fornecido.
- Resposta Bem-Sucedida: Código de status HTTP 204 (Sem conteúdo).
### Pedidos (/api/orders)
#### Listar Todos os Pedidos
- GET /api/orders: Retorna todos os pedidos feitos na plataforma.
- Resposta Bem-Sucedida: Lista de pedidos com detalhes de cada pedido.
#### Buscar Pedido por ID
- GET /api/orders/{id}: Retorna os detalhes de um pedido específico com o ID fornecido.-
- Resposta Bem-Sucedida: Detalhes do pedido com ID fornecido.
#### Criar Novo Pedido
- POST /api/orders: Cria um novo pedido na plataforma.
- Payload da Solicitação: Detalhes do pedido a ser criado.
- Resposta Bem-Sucedida: Detalhes do novo pedido criado.
#### Atualizar Status do Pedido
- PUT /api/orders/{id}: Atualiza o status de um pedido específico com base no ID fornecido.
- Payload da Solicitação: Novo status do pedido.
- Resposta Bem-Sucedida: Detalhes atualizados do pedido.
### Autenticação (/auth)
#### Registrar Novo Usuário
- POST /auth/register: Registra um novo usuário na plataforma.
- Payload da Solicitação: Detalhes do usuário a ser registrado.
- Resposta Bem-Sucedida: Detalhes do usuário registrado, incluindo um token de acesso.
#### Autenticar Usuário
- POST /auth/login: Autentica um usuário na plataforma.
- Payload da Solicitação: Credenciais de login do usuário.
- Resposta Bem-Sucedida: Detalhes do usuário autenticado, incluindo um token de acesso.

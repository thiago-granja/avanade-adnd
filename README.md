# Simulador de Batalhas RPG


Este projeto é um Simulador de Batalhas RPG 1v1 usando o sistema ADND. Os jogadores podem criar personagens, iniciar batalhas e realizar várias operações de batalha, como cálculos de ataque, defesa, iniciativa e dano, através de uma API REST. O projeto já vem pré-configurado com 6 personagens (3 heróis e 3 monstros), porém o jogador tem a liberdade de criar quaisquer opções que lhe venham à mente.


## Overview
Os personagens possuem os atributos abaixo:
-      {
        "name": ,
        "type": ,
        "hp": ,
        "strength": ,
        "defense": ,
        "agility": ,
        "diceQuantity": ,
        "diceFaces":
      }
    - Name é o nome do personagem.
    - Type se refere ao tipo, sendo hero / monster. Você sempre lutará contra um personagem do tipo monster.
    - HP é a quantidade de pontos de vida que o personagem possui. Quando a vida de um dos personagens da batalha chega a 0 a batalha acaba e um vencedor é definido.
    - Strength é sua força, influenciando nas jogadas de ataque e dano.
    - Defense é sua defesa, influenciando nas jogadas de defesa.
    - Agility é sua agilidade, influenciando em jogadas de ataque e defesa.
    - Dice Quantity e Dice Faces são a quantidade de dados e a quantidade de lados do dado, respectivamente. Um dado de 12 lados é conhecido como 1d12, por exemplo. Neste caso, diceQuantity = 1 e diceFaces = 12. É utilizado para rolagens de dano.


## Pré-requisitos

- Docker
- Docker Compose

## Configuração

1. Clone o repositório.
2. Acesse o root do projeto:
    ```bash
        cd app
3. Copie o arquivo `.env.example` para `.env`:
    ```bash
        cp .env.example .env
4. Preencha as variáveis de ambiente no arquivo .env. Por exemplo:

    ```bash
    POSTGRES_HOST=localhost
    POSTGRES_PORT=5432
    POSTGRES_DB=mydatabase
    POSTGRES_PASSWORD=mypassword
    POSTGRES_USER=myuser
    PORT=8080
    POSTGRES_URL=jdbc:postgresql://localhost:5432/mydatabase
## Como rodar
1. Inicie os serviços usando Docker Compose: 
    ```bash
        docker-compose up -d
* Nota: Se o contêiner do banco de dados parar por qualquer motivo, execute o comando acima novamente.

2. Aguarde alguns segundos para que o banco de dados inicie corretamente.

3. Após o banco de dados estar em execução, rode a aplicação com o seguinte comando:

    ```bash
    docker-compose up app
4. O aplicativo agora deve estar rodando na porta especificada (por padrão, 8080). Agora você pode usar a API para criar personagens, iniciar batalhas e muito mais.


## Endpoints

### Personagens

1. **Listar Todos os Personagens**:
    - **Endpoint**: `GET /characters`

2. **Obter um Personagem pelo Nome**:
    - **Endpoint**: `GET /characters/{name}`

3. **Criar um Novo Personagem**:
    - **Endpoint**: `POST /characters`
    - **Payload**:
      ```json
      {
        "name": "NomeDoPersonagem",
        "type": "hero", // "monster"
        "hp": 25,
        "strength": 8,
        "defense": 5,
        "agility": 2,
        "diceQuantity": 2,
        "diceFaces": 6
      }
      ```

4. **Atualizar um Personagem**:
    - **Endpoint**: `PUT /characters/{id}`
    - **Payload**:
      ```json
      {
        "id": 12345,
        "name": "NomeAtualizado",
        "type": "hero", // "monster"
        "hp": 27,
        "strength": 19,
        "defense": 16,
        "agility": 21,
        "diceQuantity": 2,
        "diceFaces": 8
      }
      ```

5. **Atualizar Parcialmente um Personagem**:
    - **Endpoint**: `PATCH /characters/{id}`
    - **Payload**:
      ```json
      {
        "id": 12345,
        "strength": 20,
        "defense": 17
      }
      ```

6. **Deletar um Personagem**:
    - **Endpoint**: `DELETE /characters/{id}`

### Batalhas

1. **Criar uma Nova Batalha**:
    - **Endpoint**: `POST /battle`
    - **Payload**:
      ```json
      {
        "player_character": ,
        "computer_character": , //opcional, caso não informado será escolhido um monstro aleatório para a batalha
      }
      ```
      - Essa rota retorna um ID que deve ser referenciado para o restante das rotas desta funcionalidade que apresentam `battle_id` ou `id`.

2. **Listar Todas as Batalhas Ativas**:
    - **Endpoint**: `GET /battle`

3. **Obter Detalhes de uma Batalha pelo ID**:
    - **Endpoint**: `GET /battle/{id}`

4. **Obter Registros/Logs de uma Batalha pelo ID da Batalha**:
    - **Endpoint**: `GET /battle/{battle_id}/logs`

5. **Realizar uma Operação em uma Batalha**:
    - **Endpoint**: `POST /battle/{battle_id}/{operation}`
   - Operações válidas incluem: `attack` (ataque), `defense` (defesa), `initiative` (iniciativa) e `damage` (dano). Também estarão referenciadas na batalha como nextStep, identificando qual é a próxima ação a ser realizada.


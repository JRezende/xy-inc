# XY INC - Teste de pontos de interesse

## Serviço para cadastro e busca de pontos de interesses.
## 1. Ambiente

  - JDK 8 -

  - Maven 3

  - MySQL - servidor de banco de dados;


## 2. Configuração

A aplicação roda por padrão na porta 8080, caso seja necessário alterar, retirar o comentário da primeira linha do application.properties e alterar o valor da porta.
 - server.port = 8080
É necessário cria um banco de dados de acordo com as configurações abaixo, que estão no application.properties.

 - spring.datasource.url=jdbc:mysql://localhost:3306/xy_inc_db?verifyServerCertificate=false&useSSL=false&requireSSL=false
 - spring.datasource.username=root
 - spring.datasource.password=admin
 - spring.datasource.driver-class-name=com.mysql.jdbc.Driver

Para criar a base de dados, deve executar o script sql 'xy-inc.sql' localizado na pasta Doc. No script está a criação da tabela e inserção dos dados abaixo:

 - 'Lanchonete' (x=27, y=12)
 - 'Posto' (x=31, y=18)
 - 'Joalheria' (x=15, y=12)
 - 'Floricultura' (x=19, y=21)
 - 'Pub' (x=12, y=8)
 - 'Supermercado' (x=23, y=6)
 - 'Churrascaria' (x=28, y=2)



## 3.Execução

Na pasta raiz do projeto, execute os comandos abaixo para instalar as dependências do projeto e gera o arquivo jar, executar os testes e radar a aplicação.

### Compilar Projeto
 - mvn clean install

### Execução dos testes
 - mvn test

### Subindo aplicação
  - mvn spring-boot:run


## 4. Serviços

Os serviços Rest disponíveis na aplicação são:

### Serviço para listar todos os Pontos de Interesse
 - **HTTP GET**
 - **http://localhost:8080/poi**
 - **Resposta**
```json
[
  {
    "id": 46,
    "name": "Lanchonete",
    "coordX": 27,
    "coordY": 12
  },
  {
    "id": 47,
    "name": "Posto",
    "coordX": 31,
    "coordY": 18
  },
  {
    "id": 48,
    "name": "Joalheria",
    "coordX": 15,
    "coordY": 12
  },
  {
    "id": 49,
    "name": "Floricultura",
    "coordX": 19,
    "coordY": 21
  },
  {
    "id": 50,
    "name": "Pub",
    "coordX": 12,
    "coordY": 8
  },
  {
    "id": 51,
    "name": "Supermercado",
    "coordX": 23,
    "coordY": 6
  },
  {
    "id": 52,
    "name": "Churrascaria",
    "coordX": 28,
    "coordY": 2
  }
]
```

### Serviço para criar um Ponto de Interesse
 - **HTTP POST**
 - **http://localhost:8080/poi**
 - **Body**(JSON)
 ```json
  {
    "name": "Novo Ponto",
    "coordX": 15,
    "coordY": 5
 }
 ```
 - **Resposta**
  ```
  Poi criado com sucesso. Poi [id=null, name=Novo Ponto, coordX=15, coordY=5]
  ```

### Serviço para buscar Pontos de Interesse de acordo com um ponto com coordenadas x e y e uma distância máxima

 - **HTTP POST**
 - **http://localhost:8080/poi/findPoiByDistanceAndPoint**
 - **Body**(JSON)
```json
 {
   "coordX": 20,
   "coordY": 10,
   "distance": 10
}
```
 - **Resposta**
```json
[
  {
    "id": 46,
    "name": "Lanchonete",
    "coordX": 27,
    "coordY": 12
  },
  {
    "id": 48,
    "name": "Joalheria",
    "coordX": 15,
    "coordY": 12
  },
  {
    "id": 50,
    "name": "Pub",
    "coordX": 12,
    "coordY": 8
  },
  {
    "id": 51,
    "name": "Supermercado",
    "coordX": 23,
    "coordY": 6
  }
]
```

## Como iniciar o cenário completo

Após clonar o repositorio, entre na pasta apiRest (cd apiRest)

Requer JDK 25 (versão usada nos testes). O Gradle é obtido automaticamente pelo `./gradlew`.

Abra três terminais, todos a partir da pasta `apiRest`, e execute um comando em cada, nesta ordem:

```bash
cd Lampada/apirest && ./gradlew bootRun
cd TV/apirest && ./gradlew bootRun
cd central/apirest && ./gradlew bootRun
```

## Requisições para a API da lâmpada (porta 8081)

### 1. Consultar situação atual

```bash
curl -X 'GET' \
  'http://localhost:8081/lampada' \
  -H 'accept: */*'
```

Resposta:
```json
{
  "ligada": false,
  "brilho": 0
}
```
Código HTTP: **200 OK**

### 2. Ligar ou desligar

```bash
curl -X 'PUT' \
  'http://localhost:8081/lampada/estado' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "ligada": true
}'
```

Resposta:
```json
{
  "ligada": true,
  "brilho": 0
}
```
Código HTTP: **200 OK**

Erro: valor que não é booleano (`"ligada": "talvez"`)
```bash
curl -X 'PUT' \
  'http://localhost:8081/lampada/estado' \
  -H 'Content-Type: application/json' \
  -d '{"ligada": "talvez"}'
```
Código HTTP: **400 Bad Request**

### 3. Ajustar brilho (0 a 100)

Só é permitido com a lâmpada ligada.

```bash
curl -X 'PUT' \
  'http://localhost:8081/lampada/brilho' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "brilho": 70
}'
```

Resposta:
```json
{
  "ligada": true,
  "brilho": 70
}
```
Código HTTP: **200 OK**

Erro: lâmpada desligada
```bash
curl -X 'PUT' 'http://localhost:8081/lampada/brilho' \
  -H 'Content-Type: application/json' -d '{"brilho": 70}'
```
Resposta (texto): `Não é possível ajustar o brilho com a lâmpada desligada`
Código HTTP: **409 Conflict**

Erro: brilho acima de 100 (ou abaixo de 0)
```bash
curl -X 'PUT' 'http://localhost:8081/lampada/brilho' \
  -H 'Content-Type: application/json' -d '{"brilho": 150}'
```
Resposta: JSON de erro de validação, com a mensagem `limite máximo de brilho é 100` (para valores negativos: `brilho não pode ser negativo`)
Código HTTP: **400 Bad Request**

Erro: corpo com valor que não é número (`{"brilho": "alto"}`)
Código HTTP: **400 Bad Request**

---------------------------------------------------

## Requisições para a API da TV (porta 8082)

A API aceita e devolve JSON. O estado fica em memória: a TV começa desligada, com volume 0 e no canal 1.

### 1. Consultar situação atual

```bash
curl -X 'GET' \
  'http://localhost:8082/tv' \
  -H 'accept: */*'
```

Resposta:
```json
{
  "ligada": false,
  "volume": 0,
  "canal": 1
}
```
Código HTTP: **200 OK**

### 2. Ligar ou desligar

```bash
curl -X 'PUT' \
  'http://localhost:8082/tv/estado' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "ligada": true
}'
```

Resposta:
```json
{
  "ligada": true,
  "volume": 0,
  "canal": 1
}
```
Código HTTP: **200 OK**

Erro: valor que não é booleano (`"ligada": "talvez"`)
```bash
curl -X 'PUT' \
  'http://localhost:8082/tv/estado' \
  -H 'Content-Type: application/json' \
  -d '{"ligada": "talvez"}'
```
Resposta: JSON de erro do Spring (`status`, `error`, `message`, `path`)
Código HTTP: **400 Bad Request**

### 3. Ajustar volume (0 a 100)

Só é permitido com a TV ligada.

```bash
curl -X 'PUT' \
  'http://localhost:8082/tv/volume' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "volume": 30
}'
```

Resposta:
```json
{
  "ligada": true,
  "volume": 30,
  "canal": 1
}
```
Código HTTP: **200 OK**

Erro: TV desligada
```bash
curl -X 'PUT' 'http://localhost:8082/tv/volume' \
  -H 'Content-Type: application/json' -d '{"volume": 30}'
```
Resposta (texto): `Não é possível alterar volume ou canal com a TV desligada`
Código HTTP: **409 Conflict**

Erro: volume acima de 100 (ou abaixo de 0)
```bash
curl -X 'PUT' 'http://localhost:8082/tv/volume' \
  -H 'Content-Type: application/json' -d '{"volume": 150}'
```
Resposta: JSON de erro de validação, com a mensagem `limite máximo do volume é 100` (para valores negativos: `volume não pode ser negativo`)
Código HTTP: **400 Bad Request**

Erro: corpo com valor que não é número (`{"volume": "alto"}`)
Código HTTP: **400 Bad Request**

### 4. Trocar de canal (a partir de 1)

Só é permitido com a TV ligada.

```bash
curl -X 'PUT' \
  'http://localhost:8082/tv/canal' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "canal": 5
}'
```

Resposta:
```json
{
  "ligada": true,
  "volume": 30,
  "canal": 5
}
```
Código HTTP: **200 OK**

Erro: TV desligada
```bash
curl -X 'PUT' 'http://localhost:8082/tv/canal' \
  -H 'Content-Type: application/json' -d '{"canal": 5}'
```
Resposta (texto): `Não é possível alterar volume ou canal com a TV desligada`
Código HTTP: **409 Conflict**

Erro: canal menor que 1
```bash
curl -X 'PUT' 'http://localhost:8082/tv/canal' \
  -H 'Content-Type: application/json' -d '{"canal": 0}'
```
Resposta: JSON de erro de validação, com a mensagem `só existem canais a partir do 1`
Código HTTP: **400 Bad Request**

Erro: corpo com valor que não é número (`{"canal": "abc"}`)
Código HTTP: **400 Bad Request**

-----------------------------------------------------------

## Requisições para a API da central (porta 8080)

A central recebe as requisições do cliente e as encaminha por HTTP para a lâmpada (8081) e a TV (8082). Ao iniciar, existe um ambiente chamado `sala` com a lâmpada e a TV. Lâmpada e TV precisam estar rodando para as rotas de dispositivos funcionarem.

### 1. Dispositivos

#### Consultar todos os dispositivos

```bash
curl -X 'GET' \
  'http://localhost:8080/dispositivos' \
  -H 'accept: */*'
```

Resposta:
```json
{
  "lampada": { "ligada": false, "brilho": 0 },
  "tv": { "ligada": false, "volume": 0, "canal": 1 }
}
```
Código HTTP: **200 OK**

#### Consultar um dispositivo

```bash
curl -X 'GET' 'http://localhost:8080/lampada' -H 'accept: */*'
curl -X 'GET' 'http://localhost:8080/tv' -H 'accept: */*'
```

Resposta (lâmpada):
```json
{ "ligada": false, "brilho": 0 }
```
Resposta (TV):
```json
{ "ligada": false, "volume": 0, "canal": 1 }
```
Código HTTP: **200 OK**

#### Controlar a lâmpada

```bash
curl -X 'PUT' 'http://localhost:8080/lampada/estado' \
  -H 'Content-Type: application/json' -d '{"ligada": true}'

curl -X 'PUT' 'http://localhost:8080/lampada/brilho' \
  -H 'Content-Type: application/json' -d '{"brilho": 70}'
```

Resposta (após os dois comandos):
```json
{ "ligada": true, "brilho": 70 }
```
Código HTTP: **200 OK**

#### Controlar a TV

```bash
curl -X 'PUT' 'http://localhost:8080/tv/estado' \
  -H 'Content-Type: application/json' -d '{"ligada": true}'

curl -X 'PUT' 'http://localhost:8080/tv/volume' \
  -H 'Content-Type: application/json' -d '{"volume": 30}'

curl -X 'PUT' 'http://localhost:8080/tv/canal' \
  -H 'Content-Type: application/json' -d '{"canal": 5}'
```

Resposta (após os três comandos):
```json
{ "ligada": true, "volume": 30, "canal": 5 }
```
Código HTTP: **200 OK**

### 2. Ambientes

Cada dispositivo só pode estar em um ambiente por vez. Os nomes de ambiente não diferenciam maiúsculas de minúsculas.

#### Listar ambientes

```bash
curl -X 'GET' 'http://localhost:8080/ambientes' -H 'accept: */*'
```

Resposta:
```json
{ "sala": ["lampada", "tv"] }
```
Código HTTP: **200 OK**

A ordem dos itens dentro da lista pode variar.

#### Criar ambiente

```bash
curl -X 'POST' 'http://localhost:8080/ambientes' \
  -H 'Content-Type: application/json' -d '{"nome": "quarto"}'
```

Resposta: mapa com todos os ambientes
```json
{ "sala": ["lampada", "tv"], "quarto": [] }
```
Código HTTP: **201 Created**

Erro: ambiente já existe (repetir o comando acima)
Resposta (texto): `Já existe um ambiente chamado: quarto`
Código HTTP: **409 Conflict**

Erro: nome em branco
```bash
curl -X 'POST' 'http://localhost:8080/ambientes' \
  -H 'Content-Type: application/json' -d '{"nome": ""}'
```
Resposta: JSON de erro de validação, com a mensagem `o nome do ambiente não pode estar em branco`
Código HTTP: **400 Bad Request**

#### Adicionar (ou mover) dispositivo para um ambiente

```bash
curl -X 'PUT' 'http://localhost:8080/ambientes/quarto/dispositivos/tv'
```

Resposta:
```json
["tv"]
```
Código HTTP: **200 OK**

Se o dispositivo estava em outro ambiente, ele é removido de lá. Após o comando, a `sala` fica só com `["lampada"]`.

Erro: ambiente inexistente (`/ambientes/estufa/dispositivos/tv`)
Resposta (texto): `Ambiente não encontrado: estufa`
Código HTTP: **404 Not Found**

Erro: dispositivo inexistente (`/ambientes/quarto/dispositivos/geladeira`)
Resposta (texto): `Dispositivo não encontrado: geladeira`
Código HTTP: **404 Not Found**

#### Remover dispositivo de um ambiente

```bash
curl -X 'DELETE' 'http://localhost:8080/ambientes/quarto/dispositivos/tv'
```

Resposta: sem corpo
Código HTTP: **204 No Content**

Erro: o dispositivo não está nesse ambiente (por exemplo, a lâmpada no `quarto`)
Resposta (texto): `O dispositivo lampada não está no ambiente quarto`
Código HTTP: **404 Not Found**

#### Ligar ou desligar todos os dispositivos de um ambiente

```bash
curl -X 'PUT' 'http://localhost:8080/ambientes/sala/estado' \
  -H 'Content-Type: application/json' -d '{"ligada": false}'
```

Resposta: situação de cada dispositivo do ambiente
```json
{
  "lampada": { "ligada": false, "brilho": 70 },
  "tv": { "ligada": false, "volume": 30, "canal": 5 }
}
```
Código HTTP: **200 OK**

Para um ambiente vazio, a resposta é `{}` com código **200 OK**.

Erro: ambiente inexistente (`/ambientes/estufa/estado`)
Código HTTP: **404 Not Found**

Erro: algum dispositivo do ambiente fora do ar
Código HTTP: **502 Bad Gateway**. A operação pode já ter sido aplicada aos dispositivos anteriores do ambiente (falha parcial).

#### Apagar ambiente

```bash
curl -X 'DELETE' 'http://localhost:8080/ambientes/quarto'
```

Resposta: sem corpo
Código HTTP: **204 No Content**

Os dispositivos do ambiente apagado continuam existindo, mas ficam sem ambiente.

Erro: ambiente inexistente (repetir o comando acima)
Resposta (texto): `Ambiente não encontrado: quarto`
Código HTTP: **404 Not Found**

-----------------------------------------------------

### FUNCIONALIDADES

IMPLEMENTADAS:

Lâmpada: liga ou desliga
Lâmpada: ajusta o brilho (0 a 100)
Lâmpada: consulta a situação atual
TV: liga ou desliga
TV: aumenta ou diminui o volume
TV: troca de canal
TV: consulta a situação atual
Central: consome as APIs dos dispositivos por requisições HTTP
Central: consulta todos os dispositivos conectados
Central: controla individualmente um dispositivo, respeitando as operações de cada tipo
Central: cria um ambiente ou apaga um ambiente
Central: adiciona ou remove um dispositivo a um ambiente
Central: liga ou desliga todos os dispositivos de um ambiente
Estado de cada dispositivo mantido em memória durante a execução
Endereços base das APIs dos dispositivos configurados de forma estática no código
Biblioteca springdoc-openapi para documentar a API dos dispositivos e da central

NÃO IMPLEMENTADAS:

Aplicação sem docker
Coleção Bruno não utilizada
Nas respostas de erro, não consegui ocultar o texto grande e filtrar apenas a "default message" do erro, dificultando a localização da mensagem de erro.
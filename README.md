
# API Documentation

## Descrição
Esta API fornece endpoints para gerenciar professores, disciplinas, alunos e matrículas em um sistema educacional.

## Versão
- **v0**

## URL Base
- **http://localhost:8080**

---

## Endpoints

### Professores

#### Listar todos os professores
- **GET** `/professores`
- **Resposta**:
  - **200 OK**: Lista de professores.

#### Criar um professor
- **POST** `/professores`
- **Corpo da Requisição**:
  ```json
  {
    "id": 1,
    "nome": "string",
    "email": "string",
    "cpf": "string"
  }
  ```
- **Resposta**:
  - **201 Created**

#### Atualizar professor por ID
- **PUT** `/professores`
- **Corpo da Requisição**:
  ```json
  {
    "id": 1,
    "nome": "string",
    "email": "string",
    "cpf": "string"
  }
  ```
- **Resposta**:
  - **204 No Content**

#### Buscar professor por ID
- **GET** `/professores/{id}`
- **Parâmetros**:
  - `id` (integer): ID do professor.
- **Resposta**:
  - **200 OK**: Detalhes do professor.

#### Deletar professor por ID
- **DELETE** `/professores/{id}`
- **Parâmetros**:
  - `id` (integer): ID do professor.
- **Resposta**:
  - **204 No Content**

---

### Disciplinas

#### Listar todas as disciplinas
- **GET** `/disciplinas`
- **Resposta**:
  - **200 OK**: Lista de disciplinas.

#### Criar uma disciplina
- **POST** `/disciplinas`
- **Corpo da Requisição**:
  ```json
  {
    "id": 1,
    "nome": "string",
    "professor": {
      "id": 1,
      "nome": "string",
      "email": "string",
      "cpf": "string"
    }
  }
  ```
- **Resposta**:
  - **201 Created**

#### Atualizar disciplina por ID
- **PUT** `/disciplinas`
- **Corpo da Requisição**:
  ```json
  {
    "id": 1,
    "nome": "string",
    "professor": {
      "id": 1,
      "nome": "string",
      "email": "string",
      "cpf": "string"
    }
  }
  ```
- **Resposta**:
  - **204 No Content**

#### Buscar disciplina por ID
- **GET** `/disciplinas/{id}`
- **Parâmetros**:
  - `id` (integer): ID da disciplina.
- **Resposta**:
  - **200 OK**: Detalhes da disciplina.

#### Deletar disciplina por ID
- **DELETE** `/disciplinas/{id}`
- **Parâmetros**:
  - `id` (integer): ID da disciplina.
- **Resposta**:
  - **204 No Content**

#### Listar disciplinas de um professor
- **GET** `/disciplinas/professor/{professorId}`
- **Parâmetros**:
  - `professorId` (integer): ID do professor.
- **Resposta**:
  - **200 OK**: Lista de disciplinas do professor.

---

### Alunos

#### Listar todos os alunos
- **GET** `/alunos`
- **Resposta**:
  - **200 OK**: Lista de alunos.

#### Criar um aluno
- **POST** `/alunos`
- **Corpo da Requisição**:
  ```json
  {
    "id": 1,
    "nome": "string",
    "email": "string",
    "cpf": "string"
  }
  ```
- **Resposta**:
  - **201 Created**

#### Atualizar aluno por ID
- **PUT** `/alunos`
- **Corpo da Requisição**:
  ```json
  {
    "id": 1,
    "nome": "string",
    "email": "string",
    "cpf": "string"
  }
  ```
- **Resposta**:
  - **204 No Content**

#### Buscar aluno por ID
- **GET** `/alunos/{id}`
- **Parâmetros**:
  - `id` (integer): ID do aluno.
- **Resposta**:
  - **200 OK**: Detalhes do aluno.

#### Deletar aluno por ID
- **DELETE** `/alunos/{id}`
- **Parâmetros**:
  - `id` (integer): ID do aluno.
- **Resposta**:
  - **204 No Content**

---

### Matrículas

#### Criar uma matrícula
- **POST** `/matriculas-alunos`
- **Corpo da Requisição**:
  ```json
  {
    "id": 1,
    "aluno": { "id": 1, "nome": "string" },
    "disciplina": { "id": 1, "nome": "string" },
    "nota1": 0.0,
    "nota2": 0.0,
    "status": "MATRICULADO"
  }
  ```
- **Resposta**:
  - **201 Created**

#### Trancar uma matrícula
- **PATCH** `/matriculas-alunos/trancar/{id}`
- **Parâmetros**:
  - `id` (integer): ID da matrícula.
- **Resposta**:
  - **204 No Content**

#### Emitir histórico do aluno
- **GET** `/matriculas-alunos/emitir-historico/{alunoId}`
- **Parâmetros**:
  - `alunoId` (integer): ID do aluno.
- **Resposta**:
  - **200 OK**: Histórico do aluno.

---

## Modelos

### Professor
```json
{
  "id": 1,
  "nome": "string",
  "email": "string",
  "cpf": "string"
}
```

### Disciplina
```json
{
  "id": 1,
  "nome": "string",
  "professor": { "id": 1, "nome": "string" }
}
```

### Aluno
```json
{
  "id": 1,
  "nome": "string",
  "email": "string",
  "cpf": "string"
}
```

### Matrícula do Aluno
```json
{
  "id": 1,
  "aluno": { "id": 1, "nome": "string" },
  "disciplina": { "id": 1, "nome": "string" },
  "nota1": 0.0,
  "nota2": 0.0,
  "status": "MATRICULADO"
}
```

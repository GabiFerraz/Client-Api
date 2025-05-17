# Client API
Essa api é parte do projeto da **Fase 4** da Especialização em Arquitetura e Desenvolvimento Java da FIAP.
Um sistema de gerenciamento de pedidos integrado com Spring e Mocrosserviços. A aplicação foi desenvolvida em **Java 21**,
utilizando **Spring Boot**, **Maven**, um banco de dados **H2** para testes, **Mockito** e **JUnit 5** para testes
unitários, **Lombok** para facilitar o desenvolvimento e documentação gerada pelo **Swagger**.

## Descrição do Projeto
O objetivo desse sistema é abranger desde a gestão de clientes e produtos até o processamento e entrega de pedidos, 
enfatizando a autonomia dos serviços, comunicação eficaz e persistência de dados isolada. Esta API é responsável pela 
gestão de clientes.

## Funcionalidades
A API permite:
- **Cadastrar, buscar, atualizar e deletar** clientes e seus respectivos dados.

## Tecnologias Utilizadas
- **Java 21**
- **Spring Boot**
- **Spring Data JPA**
- **Maven**
- **Banco de Dados H2**
- **Banco de Dados Mysql**
- **Mockito** e **JUnit 5**
- **Lombok**
- **Swagger**
- **Docker Compose**
- **Spotless**
- **Jacoco**

## Estrutura do Projeto
O projeto segue uma arquitetura modularizada, organizada nas seguintes camadas:
- `core`: Contém as regras de negócio do sistema.
- `domain`: Define as entidades principais do domínio.
- `domain.exception`: Exceções personalizadas para regras de negócio.
- `dto`: Representa as entradas e saídas de dados para a API.
- `gateway`: Interfaces para interação com o banco de dados.
- `usecase`: Contém os casos de uso do sistema.
- `usecase.exception`: Exceções personalizadas para regras de negócio.
- `entrypoint.configuration`: Configurações do sistema, incluindo tratamento de exceções.
- `entrypoint.controller`: Controladores responsáveis por expor os endpoints da API.
- `infrastructure.gateway`: Implementações das interfaces de gateway.
- `infrastructure.persistence.entity`: Representação das entidades persistidas no banco de dados.
- `infrastructure.persistence.repository`: Interfaces dos repositórios Spring Data JPA.
- `presenter`: Representação dos dados de saída para a API.

## Pré-requisitos
- Java 21
- Maven 3.6+
- IDE como IntelliJ IDEA ou Eclipse

## Configuração e Execução
1. **Clone o repositório**:
   ```bash
   git clone https://github.com/GabiFerraz/Client-Api.git
   ```
2. **Instale as dependências:**
   ```bash
   mvn clean install
   ```
3. **Execute o projeto:**
   ```bash
   mvn spring-boot:run
   ```

## Uso da API
Para visualização dos dados da api no banco de dados H2, rodar o comando: **mvn "-Dspring-boot.run.profiles=h2" spring-boot:run**
e acessar localmente o banco através do endpoint:
- **Banco H2**: http://localhost:8080/h2-console
- **Driver Class**: org.h2.Driver
- **JDBC URL**: jdbc:h2:mem:client
- **User Name**: gm
- **Password**:
Para visualização dos dados da api no banco de dados Mysql, subir o docker-compose: **docker-compose up --build**

Os endpoints estão documentados via **Swagger**:
- **Swagger UI**: http://localhost:8080/swagger-ui/index.html
- **Swagger JSON**: http://localhost:8080/v3/api-docs

### Possibilidades de Chamadas da API
1. **Cadastro de Cliente:**
```json
curl --location 'localhost:8080/api/clients' \
--header 'Content-Type: application/json' \
--data-raw '{
"name": "Gabis",
"cpf": "12345678000",
"phoneNumber": "+5584998765432",
"address": "Rua das Flores, 123",
"email": "gabis@test.com"
}'
```

2. **Busca de Cliente:**
```json
curl --location 'localhost:8080/api/clients/12345678000'
```

3. **Atualização de Cliente:**
```json
curl --location --request PUT 'localhost:8080/clients/12345678000' \
--header 'Content-Type: application/json' \
--data-raw '{
"name": "Gabis",
"phoneNumber": "+5584998765499",
"address": "Rua das Flores, 123",
"email": "gabis@gmail.com"
}'
```

4. **Delete de Cliente:**
```json
curl --location --request DELETE 'localhost:8080/clients/12345678000'
```


## Testes
Para rodar os testes unitários:
```bash
mvn test
```
Lembrando que o docker precisa estar rodando para os testes funcionarem: docker-compose up -d

**Rodar o coverage:**
   ```bash
   mvn clean package
   ```
Depois acessar pasta target/site/jacoco/index.html

O projeto inclui testes unitários, testes de integração e testes de arquitetura para garantir a qualidade e
confiabilidade da API.

## Desenvolvedora:
- **Gabriela de Mesquita Ferraz** - RM: 358745
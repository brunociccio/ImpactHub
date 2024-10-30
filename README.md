
# ImpactHub - Java Spring Boot Application

Este projeto é uma aplicação Java Spring Boot para o sistema `ImpactHub`, com um banco de dados Oracle hospedado na Azure e implantação de um Web App na Azure.

## CRED
- API Java Spring Boot com Maven e Thymeleaf desenvolvida por Bruno Ciccio - Desenolvedor Full-Stack Java e DevOps
- GitHub: https://github.com/brunociccio
- Linkedin: https://www.linkedin.com/in/bruno-ciccio/
- email: dev.bruno.ciccio@gmail.com

## Requisitos

- Conta na Azure com permissões para criar recursos
- Azure CLI instalado e configurado
- Git instalado e configurado
- Java 17+ instalado
- Maven instalado
- Visual Studio Code com as extensões da Azure

## Passo a Passo para Implantação

### 1. Clone o Repositório

#### Clone o repositório do projeto para sua máquina local:

```bash
git clone https://github.com/brunociccio/ImpactHub
cd impacthub
```

### 2. Configuração do Banco de Dados na Azure

#### Crie um Servidor Oracle na Azure:

```bash
az sql server create \
  --name seu-servidor-oracle \
  --resource-group rg-impacthub \
  --location brazilsouth \
  --admin-user seu-usuario-admin \
  --admin-password sua-senha-admin
```

#### Crie o Banco de Dados no Servidor Oracle:

```bash
az sql db create \
  --resource-group rg-impacthub \
  --server seu-servidor-oracle \
  --name impacthub-db \
  --service-objective S0
```

#### Configuração de Regras de Firewall
- Permita o acesso ao servidor Oracle de sua máquina local:

```bash
az sql server firewall-rule create \
  --resource-group rg-impacthub \
  --server seu-servidor-oracle \
  --name AllowYourIP \
  --start-ip-address <SEU_IP_INICIAL> \
  --end-ip-address <SEU_IP_FINAL>
```

### 3. Configuração da Aplicação

#### Atualize o arquivo `.env` com as informações do banco de dados Oracle e a chave da API da OpenAI

Crie um arquivo `.env` com as seguintes variáveis de ambiente:

```bash
DB_URL=jdbc:oracle:thin:@seu-servidor-oracle.database.windows.net:1521/impacthub-db
DB_USER=seu-usuario-admin
DB_PASS=sua-senha-admin
OPENAI_API_KEY=sua-chave-api-openai

```

Em seguida, no arquivo `application.properties`, configure para ler as variáveis:

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASS}
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver
spring.ai.openai.api-key=${OPENAI_API_KEY}
```
A chave da API da OpenAI (OPENAI_API_KEY) é necessária para o funcionamento correto do chatbot na página de Chat ESG.

### 4. Implantação do WebApp na Azure

#### Crie um Web App na Azure:

```bash
az webapp create \
  --resource-group rg-impacthub \
  --plan impacthub-plan \
  --name impacthub-webapp \
  --runtime "JAVA|17-java17" \
  --deployment-source-url https://github.com/seu-usuario/impacthub.git \
  --deployment-source-branch main
```

#### Configure a Conexão com o Banco de Dados no Web App:

```bash
az webapp config connection-string set \
  --resource-group rg-impacthub \
  --name impacthub-webapp \
  --settings "DATABASE_URL=jdbc:oracle:thin:@seu-servidor-oracle.database.windows.net:1521/impacthub-db;DATABASE_USERNAME=${DB_USER};DATABASE_PASSWORD=${DB_PASS}" \
  --connection-string-type SQLAzure
```

### 5. Login e Autenticação

O sistema permite o login de duas formas:
- **Login com credenciais cadastradas**: Use seu nome de usuário e senha na página de login.
- **Login via GitHub**: Utilize o botão de login via GitHub, que redireciona para a autenticação OAuth2 no GitHub.

#### Após o login:
- O usuário será redirecionado para a página de **Chat ESG** no endpoint `/chatEsg`, onde é possível interagir com o chatbot especializado em ESG.
- O botão de logout está disponível na página de chat para encerrar a sessão.
- O usuário padrão só tem acesso a página de **Login** e **Chat ESG**, já o usuário de admin tem privilégios de acessar todos os endpoints, documentação, rabbitMQ, fazer CRUD, etc

### Credenciais (cadastrada) para testar a API
- login: impacthub (usuário padrão)
- login: adminImpacthub (usuário com acesso total)
- senha: impacthub (mesma senha para testar ambos os perfis)
 
### 6. Endpoints e Testes

#### Teste os Endpoints da API no Insomnia ou Swagger

- Acesse o Swagger da API no endpoint:
  - [Swagger Documentation](https://webapp-impacthub.azurewebsites.net/docs)
  - http://localhost:8080/docs

- Acesse o backend:
  - [Página Inicial da API](https://webapp-impacthub.azurewebsites.net/home)
  - http://localhost:8080/home

- Acesse a tela de login:
  - [Página Inicial da API](https://webapp-impacthub.azurewebsites.net/custom-login)
  - http://localhost:8080/custom-login

Endpoints importantes para teste no Insomnia:
- GET /cadastro - Lista todos os cadastros
- POST /cadastro - Cria um novo cadastro
- GET /cadastro/{id} - Retorna um cadastro específico pelo ID
- PUT /cadastro/{id} - Atualiza um cadastro existente
- DELETE /cadastro/{id} - Exclui um cadastro pelo ID

#### Ordem correta dos CRUDs para testes de relacionamento:

1. /CadastroCNPJ
2. /Contato
3. /Endereco
4. /Documento
5. /Login
6. /Cadastro

### 7. Scripts de Banco de Dados

Para criar as tabelas no banco de dados Oracle, utilize o script `V1__criar_tabelas_iniciais.sql`, que se encontra na pasta `resources/db/migration`.

### Conclusão

Este projeto oferece uma solução integrada com Oracle na Azure, deploy automatizado via WebApp na Azure e autenticação via OAuth2 com GitHub, além do login tradicional. Todas as funcionalidades foram projetadas para garantir segurança e escalabilidade na aplicação.

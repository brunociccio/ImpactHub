# ImpactHub - Java Spring Boot Application

Este projeto é uma aplicação Java Spring Boot para o sistema `ImpactHub`, com um banco de dados Oracle hospedado na Azure e implantação de um Web App na Azure.

## Requisitos

- Conta na Azure com permissões para criar recursos
- Azure CLI instalado e configurado
- Git instalado e configurado
- Java 17+ instalado
- Maven instalado
- Visual Studio Code com as extensões da Azure
- API back-end desenvolvida por: Bruno Ciccio (dev.bruno.ciccio@gmail.com)
- Grupo: FIVE TECH COLLECTIVE - Projeto Impact Hub

### Integrantes:
BRUNO MATHEWS DE CICICO OLIVEIRA - RM 99097 - Java Advanced e Devops

ISABELLE CORSI - RM 97751 - Mastering Relational and Non-Relationl Database

JOSÉ LUIZ FERREIRA DUARTE - RM 99488 - Mobile Application Development

MARINA DE SOUZA CUCCO - RM 551569 - Complice, Quality Assurance and Tests e Disruptive Architectures

THALITA FACHINETE DE ALENCAR - RM 99292 - Advanced Business Development With .NET e Disruptive Architectures

## Passo a Passo para Implantação

### 1. Clone o Repositório

#### Clone o repositório do projeto para sua máquina local:

```bash
git clone https://github.com/brunociccio/ImpactHub
cd impacthub
```

### 2.Configuração do Banco de Dados na Azure

#### Crie um Servidor Oracle na Azure:

```bash
az sql server create \
  --name seu-servidor-oracle \
  --resource-group rg-impacthub \
  --location brazilsouth \
  --admin-user seu-usuario-admin \
  --admin-password sua-senha-admin
```

#### Crie o Banco de DAdos no Servidor Oracle:

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

### 3.Configuração da Aplicação

#### Atualize o arquivo application.properties com as informações do banco de dados Oracle
Para isso criei um arquivo .env aonde coloquei as credenciais e no arquivo properpeties coloquei essas credenciais em forma de variaveis por questão de segurança

```bash
spring.datasource.url=jdbc:oracle:thin:@seu-servidor-oracle.database.windows.net:1521/impacthub-db
spring.datasource.username=seu-usuario-admin
spring.datasource.password=sua-senha-admin
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver
```

### 4.Implantação do WebApp na Azure

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
  --settings "DATABASE_URL=jdbc:oracle:thin:@seu-servidor-oracle.database.windows.net:1521/impacthub-db;DATABASE_USERNAME=seu-usuario-admin;DATABASE_PASSWORD=sua-senha-admin" \
  --connection-string-type SQLAzure
```

### 5.Testando a aplicação

#### Acesse o Web App:
http://impacthub-webapp.azurewebsites.net/home para a página principal da API back-end
http://impacthub-webapp.azurewebsites.net/docs para acessar a documentação no swagger
http://impacthub-webapp.azurewebsites.net/cadastro esse endpoint já está feito o CRUD como teste

#### Teste os Endpoints da API no Insomnia
(o arquivo do Insomnia_2024-09-12.json está na pasta raíz do projeto)
- GET /cadastro - Lista todos os cadastros
- POST /cadastro - Cria um novo cadastro
- GET /cadastro/{id} - Retorna um cadastro específico pelo ID
- PUT /cadastro/{id} - Atualiza um cadastro existente
- DELETE /cadastro/{id} - Exclui um cadastro pelo ID

Vale ressaltar quando for testar os endpoints no insomnia, para fazer um CRUD na classe cadastro é necessário você criar
outros CRUDs em subclasses por conta do encapsulamento e herança, seguindo a ordem exata: 
1. /CadastroCNPJ
2. /Contato
3. /Endereco
4. /Documento
5. /Login
Por fim testar o CRUD completo na classe de Cadastro, porém, já é possível testar o CRUD em todas as classes citas acima
6. /Cadastro

Caso precise de uma instrução de criação das tabelas de maneira sequencial para teste, o arquivo com o script.sql está
dentro da pasta resources>db/migration o arquivo se chama V1__criar_tabelas_iniciais.sql
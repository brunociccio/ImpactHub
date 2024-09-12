-- Cria tabela Contato
CREATE TABLE TB_IMPACTHUB_CONTATO (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    telefone_fixo VARCHAR(20) NOT NULL,
    telefone_celular VARCHAR(20) NOT NULL
);

-- Cria tabela Endereco
CREATE TABLE TB_IMPACTHUB_ENDERECO (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cep VARCHAR(10) NOT NULL,
    logradouro VARCHAR(255) NOT NULL,
    numero INT NOT NULL,
    bairro VARCHAR(100) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    estado VARCHAR(50) NOT NULL
);

-- Cria tabela DocumentosCadastrais
CREATE TABLE TB_IMPACTHUB_DOCUMENTOS_CADASTRAIS (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cnpj VARCHAR(20) NOT NULL,
    inscricao_estadual VARCHAR(50) NOT NULL,
    data_registro_junta_comercial DATE NOT NULL
);

-- Cria tabela DocumentosAmbientais
CREATE TABLE TB_IMPACTHUB_DOCUMENTOS_AMBIENTAIS (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nivel_certificado_ambiental VARCHAR(255) NOT NULL,
    percentual_reciclavel DOUBLE NOT NULL,
    descricao_impato_ambiental VARCHAR(255)
);

-- Cria tabela DocumentosPolitica
CREATE TABLE TB_IMPACTHUB_DOCUMENTOS_POLITICA (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    inclusao_diversidade BOOLEAN NOT NULL,
    politica_anticorrupcao BOOLEAN NOT NULL,
    descricao_politica_governanca VARCHAR(255) NOT NULL
);

-- Cria tabela Documentos
CREATE TABLE TB_IMPACTHUB_DOCUMENTOS (
    id_documento BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome_documento VARCHAR(255) NOT NULL,
    tipo_documento VARCHAR(100) NOT NULL,
    descricao VARCHAR(255),
    data_emissao DATE NOT NULL,
    data_validade DATE,
    url_documento VARCHAR(255) NOT NULL,
    id_empresa BIGINT NOT NULL,
    documentos_cadastrais_id BIGINT,
    documentos_ambientais_id BIGINT,
    documentos_politica_id BIGINT,
    FOREIGN KEY (documentos_cadastrais_id) REFERENCES TB_IMPACTHUB_DOCUMENTOS_CADASTRAIS(id),
    FOREIGN KEY (documentos_ambientais_id) REFERENCES TB_IMPACTHUB_DOCUMENTOS_AMBIENTAIS(id),
    FOREIGN KEY (documentos_politica_id) REFERENCES TB_IMPACTHUB_DOCUMENTOS_POLITICA(id)
);

-- Cria tabela RecuperacaoSenha
CREATE TABLE TB_IMPACTHUB_RECUPERACAO_SENHA (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    token_recuperacao VARCHAR(255) NOT NULL,
    data_geracao_token TIMESTAMP NOT NULL,
    data_expiracao_token TIMESTAMP NOT NULL,
    pergunta_seguranca VARCHAR(255) NOT NULL,
    resposta_seguranca VARCHAR(255) NOT NULL
);

-- Cria tabela Login
CREATE TABLE TB_IMPACTHUB_LOGIN (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome_usuario VARCHAR(50) NOT NULL,
    email VARCHAR(255) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    ativo BOOLEAN NOT NULL,
    tentativas_login INT NOT NULL,
    ultima_tentativa_login TIMESTAMP,
    ultimo_login TIMESTAMP,
    recuperacao_senha_id BIGINT,
    FOREIGN KEY (recuperacao_senha_id) REFERENCES TB_IMPACTHUB_RECUPERACAO_SENHA(id)
);

-- Cria tabela CadastroCnpj
CREATE TABLE TB_IMPACTHUB_CADASTRO_CNPJ (
    id_empresa BIGINT AUTO_INCREMENT PRIMARY KEY,
    politica_ambiental VARCHAR(255) NOT NULL,
    emissao_co2 DOUBLE NOT NULL,
    uso_recursos_renovaveis BOOLEAN NOT NULL,
    politica_diversidade_inclusao VARCHAR(255) NOT NULL,
    numero_funcionarios INT NOT NULL,
    percentual_diversidade DOUBLE NOT NULL,
    politica_governanca VARCHAR(255) NOT NULL,
    conselho_independente BOOLEAN NOT NULL,
    transparencia_relatorios BOOLEAN NOT NULL,
    pontuacao_esg DOUBLE NOT NULL,
    data_cadastro DATE NOT NULL,
    status_ativo BOOLEAN NOT NULL
);

-- Cria tabela Cadastro
CREATE TABLE TB_IMPACTHUB_CADASTRO (
    id_usuario BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    data_nascimento VARCHAR(10) NOT NULL,
    cargo VARCHAR(100) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    cadastro_cnpj_id BIGINT,
    contato_id BIGINT,
    endereco_id BIGINT,
    documentos_id BIGINT,
    login_id BIGINT,
    FOREIGN KEY (cadastro_cnpj_id) REFERENCES TB_IMPACTHUB_CADASTRO_CNPJ(id_empresa),
    FOREIGN KEY (contato_id) REFERENCES TB_IMPACTHUB_CONTATO(id),
    FOREIGN KEY (endereco_id) REFERENCES TB_IMPACTHUB_ENDERECO(id),
    FOREIGN KEY (documentos_id) REFERENCES TB_IMPACTHUB_DOCUMENTOS(id_documento),
    FOREIGN KEY (login_id) REFERENCES TB_IMPACTHUB_LOGIN(id)
);

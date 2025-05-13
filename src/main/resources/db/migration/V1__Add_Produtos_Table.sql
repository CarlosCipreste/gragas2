CREATE TABLE produtos (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL CHECK (TRIM(nome) <> ''),
    descricao TEXT CHECK (TRIM(nome) <> ''),
    categoria VARCHAR(100) CHECK (TRIM(nome) <> ''),
    preco DECIMAL(10, 2) CONSTRAINT preco_positive CHECK (preco > 0.00) NOT NULL,
    quantidade_estoque INT CONSTRAINT quantidade_positive CHECK (quantidade_estoque > 0) NOT NULL,
    ativo VARCHAR(12) NOT NULL
)
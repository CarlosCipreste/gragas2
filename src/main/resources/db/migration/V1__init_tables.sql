-- Tabela de produtos
CREATE TABLE produtos (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL CHECK (TRIM(nome) <> ''),
    descricao TEXT NOT NULL CHECK (TRIM(descricao) <> ''),
    categoria VARCHAR(100) NOT NULL CHECK (TRIM(categoria) <> ''),
    preco DECIMAL(10, 2) NOT NULL CHECK (preco > 0.00),
    quantidade_estoque INT NOT NULL CHECK (quantidade_estoque >= 0),
    status VARCHAR(20) NOT NULL CHECK (
        status IN ('DISPONIVEL', 'ESGOTADO')
    ) DEFAULT 'DISPONIVEL'
);

-- Tabela de pedidos
CREATE TABLE pedidos (
    id SERIAL PRIMARY KEY,
    valor_total DECIMAL(10, 2) NOT NULL CHECK (valor_total > 0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    finished_at TIMESTAMP,
    status VARCHAR(20) NOT NULL CHECK (
        status IN (
            'PENDENTE',
            'FINALIZADO',
            'CANCELADO'
        )
    ) DEFAULT 'PENDENTE'
);

-- Tabela de item_pedido
CREATE TABLE item_pedidos (
    id SERIAL PRIMARY KEY,
    quantidade INT NOT NULL CHECK (quantidade > 0),
    produto_id INT NOT NULL,
    pedido_id INT NOT NULL,
    FOREIGN KEY (produto_id) REFERENCES produtos (id),
    FOREIGN KEY (pedido_id) REFERENCES pedidos (id)
);
CREATE DATABASE farmadb;
USE farmadb;

CREATE TABLE Pessoa (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    telefone VARCHAR(15) NOT NULL
);

CREATE TABLE Cliente (
    id INT PRIMARY KEY,
    cpf VARCHAR(14) NOT NULL,
    FOREIGN KEY (id) REFERENCES Pessoa(id) ON DELETE CASCADE
);

CREATE TABLE Fornecedor (
    id INT PRIMARY KEY,
    cnpj VARCHAR(18) NOT NULL,
    FOREIGN KEY (id) REFERENCES Pessoa(id) ON DELETE CASCADE
);

CREATE TABLE Medicamento (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,
    quantidade INT NOT NULL,
    fornecedor_id INT NOT NULL,
    FOREIGN KEY (fornecedor_id) REFERENCES Fornecedor(id) ON DELETE CASCADE
);

CREATE TABLE Venda (
    id INT PRIMARY KEY AUTO_INCREMENT,
    cliente_id INT,
    medicamento_id INT,
    quantidade INT NOT NULL,
    data DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    preco_total DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES Cliente(id) ON DELETE CASCADE,
    FOREIGN KEY (medicamento_id) REFERENCES Medicamento(id) ON DELETE CASCADE
);

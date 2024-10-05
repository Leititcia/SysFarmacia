# FarmaLab 💊

![farmlab](https://github.com/user-attachments/assets/87774172-90a5-4df3-a7b9-492dd3c95e47)

## Tópicos
* [Descrição do Sistema](#descricao-do-sistema)
* [Tecnologias Utilizadas](#tecnologias-utilizadas)
* [Estrutura de Pastas](#estrutura-de-pastas)
* [Demonstração do Sistema](#demonstracao)
* [Como Clonar](#como-clonar)
* [Autoras](#autoras)

---

<div id='descricao-do-sistema'/>

## Descrição do Sistema

**FarmaLab** é um Sistema de Gerenciamento de Farmácia que permite o cadastro e gerenciamento de medicamentos, fornecedores, clientes e vendas. O sistema foi desenvolvido com foco em fornecer uma interface gráfica simples e amigável para interagir com os dados e realizar operações de CRUD (Criar, Ler, Atualizar, Deletar) de maneira eficiente. Além disso, o sistema utiliza a persistência de dados com JDBC, armazenando informações em um banco de dados MySQL acessado via phpMyAdmin.

### Implementação

1. **Definição do Diagrama de Classes**
2. **Design das Telas no Figma**
3. **Criação do Banco de Dados**
4. **Definição dos Models**
5. **Implementação das Views**
6. **Integração com o Banco de Dados**

---

<div id='tecnologias-utilizadas'/>

## Tecnologias Utilizadas

- **Java 17**: Linguagem de programação usada para o desenvolvimento do sistema.
- **Swing**: Framework responsável pela construção da interface gráfica (GUI) do sistema.
- **MySQL**: Banco de dados relacional utilizado para armazenar todas as informações do sistema.
- **JDBC**: Biblioteca Java utilizada para conectar e realizar operações no banco de dados MySQL.
- **phpMyAdmin**: Ferramenta para gerenciamento do banco de dados MySQL.
- **Figma**: Ferramenta de design utilizada para prototipar as interfaces gráficas do sistema ([link do design](https://www.figma.com/board/GfHPuOUWYnQv8QFuNIS643/Untitled?node-id=0-1&t=SdBdeh3DlqnkuekB-17 )).
- **LucidChart**: Utilizado para a criação do diagrama de classes ([link do diagrama](https://lucid.app/lucidchart/537558ca-e8e9-44a1-ad8a-f8c537ea32ab/edit?viewport_loc=665%2C571%2C820%2C646%2C0_0&invitationId=inv_13c7bedb-c0cb-462e-9f23-1793f18afc57)).

---

<div id='estrutura-de-pastas'/>

## Estrutura de Pastas

```sh
├── SysFarmaceutico/
│     ├── src/
│          └── controllers/
│                 ├── CLienteController.java
│                 ├── FornecedorController.java
│                 ├── MedicamentoController.java
│                 └── VendaController.java 
│          └── db/
│                 ├── ConexaoBancoDeDados.java
│                 ├── config.properties
│                 └── dump.sql 
│          └── lib/
│                 └── mysql-connector-j-9.0.0.0.jar
│          └── models/
│                 ├── Cliente.java
│                 ├── Fornecedor.java
│                 ├── Medicamento.java
│                 ├── Pessoa.java
│                 └── Venda.java
│          └── view/
│                 ├── ClienteGui.java
│                 ├── FornecedorGUI.java
│                 ├── MedicamentoGUI.java
│                 └── VendaGUI.java
│      ├── Main.java
│      ├── SysFarmaceutico.iml
├── README.md
```

### Descrição dos Diretórios

- ***controllers/***: Contém as classes responsáveis pela lógica de controle de cada funcionalidade, como o gerenciamento de clientes, fornecedores, medicamentos e vendas.

- ***db/***: Diretório relacionado ao banco de dados. Contém a classe ConexaoBancoDeDados.java para estabelecer a conexão via JDBC, o arquivo de configuração config.properties e o script dump.sql para criação das tabelas e inserção de dados.

- ***lib/***: Diretório que armazena o conector JDBC (mysql-connector-j-9.0.0.0.jar), necessário para integrar a aplicação com o banco de dados MySQL.

- ***models/***: Contém as classes de modelo que representam as entidades principais do sistema, como Cliente, Fornecedor, Medicamento, Venda e a classe base Pessoa.

- ***view/***: Diretório com as interfaces gráficas (GUI) desenvolvidas com Swing, permitindo interação do usuário com o sistema para gerenciar clientes, fornecedores, medicamentos e vendas.
  
- ***Main.java***: Classe principal responsável por iniciar a aplicação.

- ***SysFarmaceutico.iml***: Arquivo de configuração do IntelliJ IDEA para o projeto.

---

<div id='demonstracao'/>

## Demonstração do Sistema

Ao clicar [aqui](https://drive.google.com/drive/folders/1bV__uiro9-5zje49aqaU_QDb-vvc7SGs?usp=sharing), você será redirecionado para uma pasta no Google Drive, onde poderá assistir a um vídeo demonstrativo do sistema em funcionamento.

---

<div id='como-clonar'/>

## Como Clonar

### Pré-requisitos

| ![Java](https://img.shields.io/badge/java-007396.svg?style=for-the-badge&logo=java&logoColor=white) | ![MySQL](https://img.shields.io/badge/mysql-005C84.svg?style=for-the-badge&logo=mysql&logoColor=white) | ![phpMyAdmin](https://img.shields.io/badge/phpMyAdmin-6C78AF.svg?style=for-the-badge&logo=phpmyadmin&logoColor=white) | ![IntelliJ](https://img.shields.io/badge/IntelliJ_IDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white) |
|:-----------------------------------:|:-----------------------------------:|:-----------------------------------:|:-----------------------------------:|
| **Java 11 ou superior**             | **Instalar MySQL**                  | **Para facilitar a administração do banco de dados (opcional)**  | **Para execução do código (ou IDE de sua preferência)** |


### Passos para clonar

1. **Clone o repositório em sua máquina local:**
```sh
git clone https://github.com/Leititcia/SysFarmacia.git
```

2. **Importe o projeto na sua IDE.**
     - Abra o IntelliJ ou sua IDE preferida e importe o projeto clonado.

4. **Configure o banco de dados MySQL:**
     - Crie o banco de dados utilizando o arquivo ```dump.sql``` localizado  no diretório ```db/```.
       
5. **Atualize as credenciais arquivo de conexão:**
     - Edite as credenciais diretamente na URL do arquivo ```ConexaoBancoDeDados.java```:
```
static {
   URL = "jdbc:mysql://127.0.0.1:3306/farmadb";
   USER = <seu_user>;
   PASSWORD = <sua_senha>;
}
```

5. **Execute o sistema:**
     - Inicie o sistema executando a classe ```Main.java```.
       
---
<div id='autoras'>

## Autoras
<table>
  <tr>
    <td align="center">
      <a href="https://github.com/Leititcia">
        <img src="https://avatars.githubusercontent.com/u/130941056?v=4" width="100px;" alt="Colaboradora Leticia"/><br>
        <sub>
          <b>Leticia Vale</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/Motaplivia">
        <img src="https://avatars.githubusercontent.com/u/144686445?v=4" width="100px;" alt="Coolaboradora Lívia"/><br>
        <sub>
          <b>Lívia Mota</b>
        </sub>
      </a>
    </td>
  </tr>
</table>

# 📚 BookWise

O **BookWise** é uma API REST desenvolvida em **Spring Boot** com autenticação via **Spring Security** e **JWT**, ideal para gerenciar empréstimos e cadastros de livros.  
O projeto utiliza **MySQL** como banco de dados, configurado via **XAMPP**, tornando o ambiente simples de instalar e configurar em máquinas Windows.

---

## 🚀 Tecnologias Utilizadas

- **[Java 17+](https://adoptium.net/)** – Linguagem base do projeto  
- **[Spring Boot](https://spring.io/projects/spring-boot)** – Framework para criação da API  
- **[Spring Security](https://spring.io/projects/spring-security)** – Para autenticação e controle de acesso  
- **[JWT (JSON Web Token)](https://jwt.io/)** – Para geração e validação de tokens de login  
- **[MySQL](https://dev.mysql.com/downloads/)** – Banco de dados relacional  
- **[XAMPP](https://www.apachefriends.org/)** – Ambiente de desenvolvimento para rodar o MySQL localmente  

---

## 🖥️ Como Configurar o Ambiente no Windows

### 1️⃣ Instalar o Java

1. Baixe o instalador do Java [clicando aqui](https://adoptium.net/).
2. Instale normalmente e, após a instalação, abra o Prompt de Comando e digite:
   ```
   java -version
   ```

3. Você deve ver algo como:
    ```
    openjdk version "17.x.x"
    ```
### 2️⃣ Instalar o Git

1. Baixe o instalador do Git [clicando aqui](https://git-scm.com/downloads).
2. Durante a instalação, marque a opção "Add Git to PATH".
3. Verifique a instalação:
    ```
    git --version
    ```

### 3️⃣ Instalar o XAMPP + MySQL

1. Baixe o XAMPP [clicando aqui](https://www.apachefriends.org/).
2. Instale e abra o XAMPP Control Panel.
3. Inicie os módulos Apache e MySQL.
4. No MySQL, crie um banco de dados para o projeto:
    ```
    CREATE DATABASE bookwise_db;
    ```

### 4️⃣ Configurar o Projeto

1. Clone este repositório:
    ```
    git clone https://github.com/matheusaugusto98521/bookWise-api.git
    ```
2. Abra o projeto em sua IDE (IntelliJ / Eclipse / VS Code).

### 5️⃣ Executar o Projeto

Na IDE ou pelo terminal, na raíz do projeto, execute:
    ```
    ./mvnw spring-boot:run
    ```

Ou, se você usa Maven instalado globalmente:
    ```
    mvn spring-boot:run
    ```

## 🔑 Autenticação com JWT

Este projeto utiliza JSON Web Tokens para autenticação.
Fluxo básico:

1. O usuário faz login enviando email/senha para /security/login.
2. A API retorna um token JWT.
3. As próximas requisições devem incluir o token no header:
    ```
    Authorization: Bearer SEU_TOKEN_AQUI
    ```

## 📖 Endpoints Principais

Os endpoints serão listados [aqui](), com um arquivo markdown explicando as rotas e como será enviado o corpo da requisição.

## 🤝 Contribuição

#### Sinta-se à vontade para abrir issues ou enviar pull requests para melhorias no projeto.

## 📜 Licença

Este projeto é de uso livre para estudo e desenvolvimento.


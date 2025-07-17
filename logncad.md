# LognCad: Documento de Visão e Arquitetura v1.0

**Autor:** Pedro Henrique Garcia (phkaiser13)
**Data:** 2025
**Status:** Em Concepção

## 1. Visão Geral do Projeto

**LognCad** é um sistema modular e robusto para autenticação e gerenciamento de usuários. Nascido da necessidade de uma solução de login segura, rápida e de fácil integração, o projeto foca em uma experiência de usuário limpa e uma arquitetura de back-end performática e de fácil manutenção.

O sistema permitirá que os usuários criem uma conta com um nome de usuário e senha, e subsequentemente se autentiquem para acessar uma área restrita (a ser definida pela aplicação que o consumir). O diferencial está na nossa stack tecnológica moderna (Kotlin/Java) e na escolha de um formato de serialização de dados eficiente e compacto: **MessagePack**.

## 2. Funcionalidades Principais (MVP - Mínimo Produto Viável)

- **Cadastro de Usuário:** Um novo usuário poderá se registrar fornecendo um nome de usuário único e uma senha segura.
- **Login de Usuário:** Um usuário registrado poderá se autenticar usando suas credenciais.
- **Persistência de Dados:** As informações dos usuários serão salvas e carregadas de forma segura e eficiente, utilizando o formato MessagePack.
- **Feedback Visual:** A interface fornecerá feedback claro ao usuário em caso de sucesso (login/cadastro) ou erro (usuário já existente, senha incorreta, etc.).
- **Interface Gráfica (GUI):** Uma interface de usuário moderna, minimalista e intuitiva, construída com JavaFX e estilizada para proporcionar uma experiência agradável.

## 3. Visão da Arquitetura

Adotaremos uma arquitetura de 3 camadas (Apresentação, Lógica de Negócio, Acesso a Dados) para garantir a separação de responsabilidades, o que facilita testes, manutenção e futuras expansões.

1.  **Camada de Apresentação (Frontend - GUI):**
    *   **Tecnologia:** JavaFX. Utilizaremos o FXML para declarar a estrutura da UI de forma declarativa, separando-a da lógica de controle.
    *   **Filosofia:** Design minimalista e centrado no usuário. Foco em usabilidade e feedback instantâneo. Utilizaremos CSS para estilização, permitindo a criação de temas (ex: Dark/Light) no futuro.

2.  **Camada de Lógica de Negócio (Backend - Core):**
    *   **Tecnologia:** Kotlin. Ideal por sua concisão, segurança (null-safety) e interoperabilidade total com Java.
    *   **Componentes:**
        *   `AuthService`: Responsável por orquestrar as operações de login e cadastro.
        *   `UserService`: Gerencia a lógica relacionada ao usuário (validações, criação).
        *   `PasswordService`: Abstrai a lógica de hashing e verificação de senhas (essencial para segurança).

3.  **Camada de Acesso a Dados (Persistence):**
    *   **Tecnologia:** Java/Kotlin.
    *   **Formato:** MessagePack. Escolhido por ser um formato de serialização binário, o que o torna mais rápido e mais compacto que JSON ou XML, ideal para performance em operações de I/O.
    *   **Componente:**
        *   `UserRepository`: Interface que define o contrato para salvar, buscar e verificar a existência de usuários.
        *   `MessagePackUserRepository`: Implementação concreta que manipula a leitura e escrita do arquivo `users.msgpack`.

## 4. Stack Tecnológica

- **Linguagens:** Kotlin (para a lógica de negócio) e Java (para a base da aplicação e interoperabilidade).
- **Framework de UI:** JavaFX 21+.
- **Build Tool:** Gradle, por sua flexibilidade e excelente suporte a projetos Kotlin/Java.
- **Biblioteca de Persistência:** `org.msgpack:msgpack-core`.
- **Biblioteca de Hashing de Senha:** `org.mindrot:jbcrypt` ou uma implementação nativa mais moderna.

## 5. Conceito Visual / Telas (Mockups)

Imaginamos uma interface com uma paleta de cores sóbria (tons de cinza, azul escuro) e um design "flat".

#### Tela 1: Login (Estado Padrão)

-   **Logo:** Logo do "LognCad" no topo.
-   **Campo 1:** "Nome de Usuário" (com ícone de usuário).
-   **Campo 2:** "Senha" (com ícone de cadeado, e opção de mostrar/ocultar).
-   **Botão 1:** "Entrar" (botão primário, cor de destaque).
-   **Link/Botão 2:** "Não tem uma conta? **Cadastre-se**" (texto secundário que leva à Tela 2).

#### Tela 2: Cadastro

-   **Logo:** Logo do "LognCad".
-   **Título:** "Crie sua Conta".
-   **Campo 1:** "Nome de Usuário".
-   **Campo 2:** "Senha".
-   **Campo 3:** "Confirmar Senha".
-   **Botão 1:** "Cadastrar" (botão primário).
-   **Link/Botão 2:** "Já tem uma conta? **Faça o login**" (leva de volta à Tela 1).

#### Tela 3: Bem-vindo (Pós-Login)

-   **Título:** "Bem-vindo, [NomeDoUsuario]!".
-   **Informação:** "Você está logado no sistema LognCad."
-   **Botão:** "Sair" (Logout).

## 6. Próximos Passos

1.  **Setup do Projeto:** Configuração do projeto Gradle com as dependências necessárias (JavaFX, Kotlin, MessagePack).
2.  **Modelo de Dados:** Criação da classe `User` em Kotlin.
3.  **Serviço de Persistência:** Implementação da classe que irá ler e escrever a lista de usuários no arquivo `.msgpack`.
4.  **Serviços de Negócio:** Implementação da lógica de cadastro e login.
5.  **Desenvolvimento da GUI:** Criação das telas FXML e seus respectivos Controllers.
6.  **Integração e Testes:** Conectar a GUI com os serviços de backend e realizar testes de ponta a ponta.
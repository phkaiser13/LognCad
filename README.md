# LognCad - Sistema de Login e Cadastro Simples em Kotlin e JavaFX Usando MessagePack

## 📖 Sobre o Projeto

**LognCad** é uma aplicação de desktop simples e funcional que implementa um sistema de autenticação de usuários. O projeto foi desenvolvido com o objetivo de ser um material de estudo prático, demonstrando conceitos fundamentais da criação de aplicações com múltiplas telas, manipulação de eventos e separação de responsabilidades (MVC).

É um excelente ponto de partida para quem deseja aprender a construir aplicações de desktop modernas utilizando **Kotlin** e **JavaFX**.

---

## ✨ Funcionalidades

* **Tela de Login**: Permite que usuários existentes acessem o sistema.
* **Tela de Cadastro**: Permite que novos usuários criem uma conta.
* **Validação de Senha**: Verifica se a senha e a confirmação de senha são idênticas no momento do cadastro.
* **Navegação Centralizada**: Transição limpa e reutilizável entre as telas.
* **Interface Estilizada**: O design da aplicação é customizado via CSS para uma aparência mais agradável.
* **Persistência de Dados**: Os usuários são salvos localmente em um arquivo protegido e criptografado nativamente, a famosa alternativa ao json "MessagePack", permitindo que os cadastros persistam entre as execuções do programa.

---

## 🛠️ Tecnologias Utilizadas

* **Linguagem**: [Kotlin](https://kotlinlang.org/)
* **Framework Gráfico**: [JavaFX](https://openjfx.io/)
* **Gerenciador de Dependências**: [Gradle](https://gradle.org/)
* * **Alternativa ao Json comum**: [MessagePack]
* **IDE Recomendada**: [IntelliJ IDEA](https://www.jetbrains.com/idea/)

---

## 📂 Estrutura do Projeto

O projeto segue uma estrutura baseada no padrão Model-View-Controller (MVC) para manter o código organizado e de fácil manutenção.

```
LognCad/
├── src/
│   ├── main/
│   │   ├── kotlin/
│   │   │   └── com/logncad/
│   │   │       ├── App.kt                 # Ponto de entrada da aplicação
│   │   │       ├── controller/
│   │   │       │   ├── LoginController.kt     # Lógica da tela de login
│   │   │       │   └── RegisterController.kt  # Lógica da tela de cadastro
│   │   │       ├── model/
│   │   │       │   └── User.kt                # Modelo de dados do usuário
│   │   │       ├── repository/
│   │   │       │   └── UserRepository.kt      # Interface para acesso aos dados
│   │   │       ├── service/
│   │   │       │   └── AuthService.kt         # Lógica de negócio (autenticação)
│   │   │       └── util/
│   │   │           └── Navigator.kt         # Classe para gerenciar a navegação
│   │   └── resources/
│   │       └── com/logncad/
│   │           └── view/
│   │               ├── LoginView.fxml         # UI da tela de login
│   │               ├── RegisterView.fxml      # UI da tela de cadastro
│   │               └── style.css            # Folha de estilos
│   └── test/
└── build.gradle.kts                         # Arquivo de build do Gradle
```

---

## 🚀 Como Executar o Projeto

### Pré-requisitos

* **Java Development Kit (JDK)**: Versão 11 ou superior.
* **IntelliJ IDEA**: Versão Community ou Ultimate.

### Passos

1.  **Clone o repositório:**
    ```bash
    git clone <https://github.com/phkaiser13/LognCad.git>
    ```
2.  **Abra o projeto no IntelliJ IDEA:**
    * Vá em `File > Open...` e selecione a pasta `LognCad` que você acabou de clonar.
    * O IntelliJ irá detectar o arquivo `build.gradle.kts` e sincronizar as dependências do projeto automaticamente.
3.  **Execute a aplicação:**
    * Navegue até o arquivo `src/main/kotlin/com/logncad/App.kt`.
    * Clique na seta verde ao lado da declaração `fun main()` e selecione `Run 'App.kt'`.

A tela de login da aplicação deverá ser exibida.

---

## 💡 Detalhes da Implementação

* **Views (FXML)**: As interfaces são declaradas em arquivos `.fxml`, separando o design da lógica. Isso permite que a UI seja modificada sem alterar o código Kotlin.
* **Controllers**: As classes `LoginController` e `RegisterController` são responsáveis por receber os eventos da UI (como cliques de botão) e orquestrar as ações necessárias.
* **Navigator**: A classe `Navigator` centraliza a lógica de troca de telas. Isso evita código duplicado nos controllers e torna a navegação mais simples de manter.
* **AuthService**: Centraliza a lógica de negócio, como validar credenciais e registrar novos usuários, desacoplando-a dos controllers.

---

## 🌱 Possíveis Implementações/Adições

Este projeto é uma base. Sinta-se à vontade para expandi-lo com novas funcionalidades:

* [ ] Implementar um banco de dados (como H2, SQLite) em vez de salvar em arquivo.
* [ ] Adicionar criptografia adicional (como hashing) para as senhas antes de salvá-las.
* [ ] Criar uma tela de "Boas-vindas" para ser exibida após o login bem-sucedido.
* [ ] Implementar uma funcionalidade "Lembrar-me" na tela de login.
* [ ] Adicionar mais validações nos campos de entrada (ex: e-mail válido, força da senha).

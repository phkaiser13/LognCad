// 2025
// By Pedro henrique garcia.
// Github/gitlab: Phkaiser13

// A seção de plugins ativa funcionalidades essenciais para o nosso build.
plugins {
    // Aplica o plugin do Kotlin para a JVM. Essencial para compilar nosso código Kotlin.
    kotlin("jvm") version "1.9.23"

    // Aplica o plugin 'application', que nos ajuda a configurar e executar nossa aplicação
    // como um programa standalone. Ele nos dará tarefas como 'run'.
    application

    // Plugin específico para JavaFX. Ele simplifica enormemente a configuração das
    // dependências e os módulos do JavaFX, cuidando da complexidade por baixo dos panos.
    id("org.openjfx.javafxplugin") version "0.1.0"
}

// 'group' é um identificador para o seu projeto, geralmente o seu domínio reverso.
group = "com.logncad"
// 'version' é a versão atual do nosso software.
version = "1.0.0"

// Aqui definimos os repositórios de onde o Gradle irá baixar nossas dependências.
repositories {
    // Maven Central é o repositório principal e mais comum para bibliotecas Java/Kotlin.
    mavenCentral()
}

// Configurações específicas para o plugin 'application'.
application {
    // Aqui definimos a classe principal que contém o método 'main', o ponto de entrada da nossa aplicação.
    // É crucial que este caminho corresponda à estrutura de pacotes que vamos criar.
    mainClass.set("com.logncad.AppKt") // Usaremos um arquivo Kotlin App.kt como ponto de entrada
}

// Configurações específicas para o plugin do JavaFX.
javafx {
    // A versão do JavaFX que queremos usar. É bom manter atualizado.
    version = "21"
    // O JavaFX é modular. Aqui listamos apenas os módulos que nossa aplicação precisa.
    // Isso mantém o tamanho final da nossa aplicação menor e mais otimizado.
    modules = listOf("javafx.controls", "javafx.fxml")
}

// A seção de 'dependencies' é onde declaramos todas as bibliotecas de terceiros que nosso projeto precisa.
dependencies {
    // ----- Bibliotecas Principais da Stack -----

    // Inclui a biblioteca padrão do Kotlin, necessária para qualquer projeto Kotlin.
    implementation(kotlin("stdlib"))

    // A biblioteca do MessagePack para Java. Será usada na nossa camada de persistência
    // para serializar e deserializar os objetos de usuário de forma eficiente.
    implementation("org.msgpack:msgpack-core:0.9.8")

    // Biblioteca para hashing de senhas. A segurança é primordial.
    // jBCrypt é uma implementação conhecida e confiável do algoritmo bcrypt.
    // NUNCA, JAMAIS, em hipótese alguma, armazene senhas em texto plano.
    implementation("org.mindrot:jbcrypt:0.4")


    // ----- Dependências de Teste -----

    // Define o JUnit 5 como nosso framework de testes.
    // 'testImplementation' significa que esta dependência só estará disponível no código de teste.
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.2")
}

// Configura a tarefa de teste para usar a plataforma do JUnit 5.
tasks.withType<Test> {
    useJUnitPlatform()
}
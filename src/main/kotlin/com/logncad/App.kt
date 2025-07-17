// 2025
// By Pedro henrique garcia.
// Github/gitlab: Phkaiser13

package com.logncad

import com.logncad.repository.MessagePackUserRepository
import com.logncad.service.AuthService
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import java.net.URL

/**
 * A classe principal da nossa aplicação LognCad.
 * Esta classe HERDA da classe 'javafx.application.Application', que é uma classe Java.
 * Este é um exemplo perfeito da interoperabilidade entre Kotlin e Java.
 *
 * Sua responsabilidade é configurar o "palco" inicial (a janela) e carregar a primeira
 * cena da nossa aplicação (a tela de Login).
 */
class App : Application() {

    // companion object é o lar de membros que pertencem à classe, não a instâncias dela,
    // semelhante ao 'static' em Java.
    companion object {
        // Tornamos o authService acessível globalmente de uma forma simples.
        // Em aplicações maiores, usaríamos um framework de Injeção de Dependência
        // (como Guice, Dagger ou Koin), mas para este escopo, isso é suficiente e claro.
        // O 'lateinit' nos permite declarar uma propriedade não-nula que será inicializada depois.
        lateinit var authService: AuthService
    }

    /**
     * O método 'init()' é chamado ANTES do 'start()'.
     * É o local ideal para inicializar a lógica de "background" da nossa aplicação,
     * como nossos serviços e repositórios.
     */
    override fun init() {
        // Este é o nosso "ponto de montagem" ou "injeção de dependência" manual.
        // 1. Criamos a implementação concreta do nosso repositório.
        val userRepository = MessagePackUserRepository()
        // 2. Criamos o serviço de autenticação, passando o repositório como dependência.
        authService = AuthService(userRepository)
    }

    /**
     * O ponto de entrada para todas as aplicações JavaFX.
     * Este método é chamado após o 'init()' e recebe o 'Stage' principal (a janela).
     *
     * @param primaryStage O palco principal fornecido pelo framework JavaFX.
     */
    override fun start(primaryStage: Stage) {
        // Carregamos a nossa visão (a UI) de um arquivo FXML.
        // Separar a UI (FXML) da lógica (Controller/App) é uma prática fundamental
        // para manter o código organizado.
        val fxmlUrl: URL = App::class.java.getResource("view/LoginView.fxml")
            ?: throw IllegalStateException("Arquivo FXML não encontrado.")

        val root: Parent = FXMLLoader.load(fxmlUrl)

        // Título da janela da nossa aplicação.
        primaryStage.title = "LognCad - Autenticação"

        // Criamos a "cena" com o conteúdo carregado do FXML e a colocamos no "palco".
        primaryStage.scene = Scene(root, 350.0, 400.0) // Definimos um tamanho inicial

        // Impede o usuário de redimensionar a janela, para manter o layout consistente.
        primaryStage.isResizable = false

        // Exibe o palco para o usuário.
        primaryStage.show()
    }
}

/**
 * A função 'main' que serve como ponto de entrada para a JVM.
 * No Kotlin, ela pode ficar fora de uma classe. Ela simplesmente delega o lançamento
 * da aplicação para o framework JavaFX.
 */
fun main() {
    Application.launch(App::class.java)
}
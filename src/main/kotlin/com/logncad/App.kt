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

    // companion object é o lar de membros que pertencem à classe, não a instâncias dela.
    // É o local ideal para o ponto de entrada da aplicação (a função 'main').
    companion object {
        // Tornamos o authService acessível globalmente de uma forma simples.
        lateinit var authService: AuthService

        /**
         * O ponto de entrada (main) da nossa aplicação.
         * A anotação @JvmStatic é crucial aqui. Ela instrui o compilador Kotlin a gerar um
         * método 'public static void main(String[] args)' real na classe 'App', que é
         * o formato que a JVM e o JavaFX procuram para iniciar a execução.
         *
         * @param args Argumentos da linha de comando, que passamos para o JavaFX.
         */
        @JvmStatic
        fun main(args: Array<String>) {
            // Delega o lançamento da aplicação para o framework JavaFX.
            // Passamos a nossa classe 'App' e os argumentos recebidos.
            launch(App::class.java, *args)
        }
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
        // O caminho para o nosso arquivo de layout FXML.
        // A barra "/" no início indica que o caminho é a partir da raiz do 'resources'.
        val fxmlUrl: URL = App::class.java.getResource("/com/logncad/view/LoginView.fxml")
            ?: throw IllegalStateException("Arquivo FXML não encontrado. Verifique o caminho em src/main/resources.")

        val root: Parent = FXMLLoader.load(fxmlUrl)

        primaryStage.title = "LognCad - Autenticação"
        primaryStage.scene = Scene(root, 350.0, 400.0)
        primaryStage.isResizable = false
        primaryStage.show()
    }
}
// 2025
// By Pedro henrique garcia.
// Github/gitlab: Phkaiser13

package com.logncad.controller

import com.logncad.App
import com.logncad.service.AuthResult
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.paint.Color
import javafx.stage.Stage
import java.io.IOException

/**
 * Controla a lógica da tela de login (LoginView.fxml).
 * Suas responsabilidades são:
 * - Capturar a entrada do usuário (nome de usuário e senha).
 * - Invocar o AuthService para processar a tentativa de login.
 * - Atualizar a UI com feedback (mensagens de erro, transição de tela).
 * - Navegar para outras telas (Registro, Tela de Boas-Vindas).
 */
class LoginController {

    // A anotação @FXML é usada para injetar os componentes definidos no arquivo FXML
    // diretamente nestas propriedades. O nome da propriedade DEVE ser o mesmo do 'fx:id'.
    // 'lateinit' indica que a variável será inicializada mais tarde (pelo FXMLLoader).
    @FXML
    private lateinit var usernameField: TextField

    @FXML
    private lateinit var passwordField: PasswordField

    @FXML
    private lateinit var feedbackLabel: Label

    /**
     * Este método é chamado pelo framework JavaFX quando o FXML é carregado, mas ANTES da UI
     * ser exibida. É um bom lugar para configurar o estado inicial dos componentes.
     */
    @FXML
    fun initialize() {
        // Garante que o label de feedback comece vazio e invisível.
        feedbackLabel.text = ""
    }

    /**
     * Manipula o evento de clique do botão "Entrar".
     * Esta função é linkada através do atributo 'onAction="#handleLogin"' no FXML.
     */
    @FXML
    private fun handleLogin() {
        val username = usernameField.text
        val password = passwordField.text

        // Delega a lógica de autenticação para o nosso serviço.
        val result = App.authService.login(username, password)

        // O 'when' do Kotlin é perfeito para lidar com nossa sealed class AuthResult.
        // Ele nos força a tratar todos os cenários, tornando nosso código mais seguro.
        when (result) {
            is AuthResult.Success -> {
                // Login bem-sucedido!
                println("Login bem-sucedido para o usuário: ${result.user.username}")
                feedbackLabel.text = "Login efetuado com sucesso!"
                feedbackLabel.textFill = Color.GREEN
                // Em um app real, navegaríamos para a tela principal aqui.
                // Exemplo: loadScene("MainDashboardView.fxml")
            }
            is AuthResult.Failure -> {
                // Login falhou, exibe a mensagem de erro específica.
                feedbackLabel.text = result.message
                feedbackLabel.textFill = Color.web("#e74c3c") // Cor vermelha para erros
            }
        }
    }

    /**
     * Manipula o clique no hyperlink para navegar para a tela de registro.
     */
    @FXML
    private fun handleSwitchToRegister() {
        println("Navegando para a tela de Registro...")
        loadScene("RegisterView.fxml")
    }

    /**
     * Função utilitária para carregar e exibir uma nova cena (tela) na janela atual.
     * Isso nos permite trocar de tela (ex: de Login para Registro) de forma limpa.
     *
     * @param fxmlFile O nome do arquivo .fxml a ser carregado. Ex: "RegisterView.fxml"
     */
    private fun loadScene(fxmlFile: String) {
        try {
            val fxmlPath = "/com/logncad/view/$fxmlFile"
            val url = javaClass.getResource(fxmlPath)
                ?: throw IOException("Não foi possível encontrar o arquivo FXML: $fxmlFile")

            val root: Parent = FXMLLoader.load(url)
            // Para obter o Stage (a janela), pegamos a cena de qualquer componente
            // já existente na tela e, a partir da cena, obtemos a janela.
            val stage = usernameField.scene.window as Stage
            stage.scene = Scene(root)
            stage.show()
        } catch (e: IOException) {
            e.printStackTrace()
            // Em um app real, mostraríamos um alerta de erro para o usuário.
            feedbackLabel.text = "Erro ao carregar a tela."
        }
    }
}
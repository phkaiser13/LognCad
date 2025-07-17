// 2025
// By Pedro henrique garcia.
// Github/gitlab: Phkaiser13

package com.logncad.controller

import com.logncad.App
import com.logncad.service.AuthResult
import com.logncad.util.Navigator // Importe o Navigator
import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField

/**
 * O Controller para a LoginView.fxml.
 * Sua responsabilidade é lidar com as interações do usuário (cliques, digitação)
 * na tela de login, comunicar-se com a camada de serviço e atualizar a UI com o resultado.
 */
class LoginController {

    @FXML
    private lateinit var usernameField: TextField

    @FXML
    private lateinit var passwordField: PasswordField

    @FXML
    private lateinit var feedbackLabel: Label


    /**
     * Este método é acionado quando o usuário clica no botão "Entrar" ou pressiona Enter.
     */
    @FXML
    private fun handleLogin() {
        val username = usernameField.text
        val password = passwordField.text

        val result = App.authService.login(username, password)

        when (result) {
            is AuthResult.Success -> {
                updateFeedback("Login bem-sucedido! Bem-vindo, ${result.user.username}.", isError = false)
                // TODO: Em uma aplicação real, aqui navegaríamos para a próxima tela.
            }
            is AuthResult.Failure -> {
                updateFeedback(result.message, isError = true)
            }
        }
    }

    /**
     * Este método é acionado quando o usuário clica no hyperlink "Cadastre-se".
     * ESTA É A ÚNICA VERSÃO QUE DEVE EXISTIR DESTE MÉTODO.
     */
    @FXML
    private fun handleSwitchToRegister() {
        // Passamos o nome do arquivo FXML de destino e um nó da cena atual.
        Navigator.navigateTo("RegisterView.fxml", usernameField)
    }

    /**
     * Uma função auxiliar para atualizar o rótulo de feedback na UI.
     */
    private fun updateFeedback(message: String, isError: Boolean) {
        feedbackLabel.text = message
        feedbackLabel.styleClass.removeAll("feedback-success", "feedback-error")

        if (message.isNotEmpty()) {
            if (isError) {
                feedbackLabel.styleClass.add("feedback-error")
            } else {
                feedbackLabel.styleClass.add("feedback-success")
            }
        }
    }
}
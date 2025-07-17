// 2025
// By Pedro henrique garcia.
// Github/gitlab: Phkaiser13

package com.logncad.controller

import com.logncad.App
import com.logncad.service.AuthResult
import com.logncad.util.Navigator
import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField

class RegisterController {

    @FXML private lateinit var usernameField: TextField
    @FXML private lateinit var passwordField: PasswordField
    @FXML private lateinit var confirmPasswordField: PasswordField
    @FXML private lateinit var feedbackLabel: Label

    /**
     * Lida com o clique no botão "Cadastrar".
     */
    @FXML
    private fun handleRegister() {
        val username = usernameField.text
        val password = passwordField.text
        val confirmPassword = confirmPasswordField.text

        // 1. Validação primária na própria tela (Client-side validation)
        if (password != confirmPassword) {
            updateFeedback("As senhas não correspondem.", isError = true)
            return // Interrompe a execução se as senhas forem diferentes
        }

        // 2. Chama o serviço de autenticação para tentar o registro
        val result = App.authService.register(username, password)

        // 3. Trata o resultado e atualiza a UI
        when (result) {
            is AuthResult.Success -> {
                updateFeedback("Cadastro realizado com sucesso! Você já pode fazer o login.", isError = false)
                // Limpa os campos após o sucesso
                usernameField.clear()
                passwordField.clear()
                confirmPasswordField.clear()
            }
            is AuthResult.Failure -> {
                updateFeedback(result.message, isError = true)
            }
        }
    }

    /**
     * Lida com o clique no hyperlink para voltar à tela de login.
     * Utiliza nosso Navigator para manter o código limpo.
     */
    @FXML
    private fun handleSwitchToLogin() {
        Navigator.navigateTo("LoginView.fxml", usernameField)
    }

    /**
     * Função auxiliar para atualizar o rótulo de feedback.
     * (Idêntica à do LoginController, em um projeto maior poderia ser movida para uma classe base)
     */
    private fun updateFeedback(message: String, isError: Boolean) {
        feedbackLabel.text = message
        feedbackLabel.styleClass.removeAll("feedback-success", "feedback-error")
        if (message.isNotEmpty()) {
            feedbackLabel.styleClass.add(if (isError) "feedback-error" else "feedback-success")
        }
    }
}
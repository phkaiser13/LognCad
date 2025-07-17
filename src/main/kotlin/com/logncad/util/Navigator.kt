// 2025
// By Pedro henrique garcia.
// Github/gitlab: Phkaiser13

package com.logncad.util

import com.logncad.App
import javafx.fxml.FXMLLoader
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import java.io.IOException

/**
 * Um objeto singleton para gerenciar a navegação entre as cenas (telas) da aplicação.
 * Usar um 'object' em Kotlin cria uma única instância (Singleton Pattern), o que é perfeito
 * para classes utilitárias como esta.
 *
 * Centralizar a lógica de navegação aqui segue o princípio DRY (Don't Repeat Yourself).
 */
object Navigator {

    /**
     * Carrega e exibe uma nova cena na janela principal.
     *
     * @param fxmlFile O nome do arquivo FXML a ser carregado (ex: "RegisterView.fxml").
     * @param currentNode Um nó qualquer da cena atual (como um botão ou campo de texto).
     *                    Usamos este nó para obter uma referência à janela (Stage) atual.
     */
    fun navigateTo(fxmlFile: String, currentNode: Node) {
        try {
            // A CORREÇÃO ESTÁ AQUI: Adicionamos a barra e o caminho completo.
            val resourcePath = "/com/logncad/view/$fxmlFile"
            val resourceUrl = App::class.java.getResource(resourcePath)
                ?: throw IOException("Não foi possível encontrar o arquivo FXML: $resourcePath")

            val root: Parent = FXMLLoader.load(resourceUrl)

            val scene = currentNode.scene
            scene.root = root

        } catch (e: IOException) {
            println("Erro ao carregar a tela: ${e.message}")
            e.printStackTrace()
        }
    }
}
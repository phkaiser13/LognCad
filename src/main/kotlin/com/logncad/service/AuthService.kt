// 2025
// By Pedro henrique garcia.
// Github/gitlab: Phkaiser13

package com.logncad.service

import com.logncad.model.User
import com.logncad.repository.UserRepository
import org.mindrot.jbcrypt.BCrypt
import java.util.UUID

/**
 * Uma classe selada (sealed class) para representar os possíveis resultados das operações de
 * autenticação. Este é um padrão de projeto poderoso em Kotlin.
 *
 * Por que usar uma `sealed class` em vez de um simples `Boolean` ou de lançar exceções?
 * 1. **Clareza:** O tipo de retorno de uma função informa exatamente o que pode acontecer:
 *    sucesso ou uma de várias falhas específicas.
 * 2. **Segurança de Tipo (Type Safety):** Ao usar um `when` para verificar o resultado,
 *    o compilador do Kotlin nos OBRIGA a tratar todos os casos possíveis (Success e todas as
 *    variações de Failure). Isso previne bugs onde esquecemos de tratar um erro.
 * 3. **Sem Exceções para Controle de Fluxo:** Lançar exceções para erros esperados (como "usuário
 *    inválido") é considerado um anti-padrão. Exceções devem ser para... bem, casos excepcionais.
 */
sealed class AuthResult {
    // Representa um resultado bem-sucedido, carregando o usuário que foi logado/registrado.
    data class Success(val user: User) : AuthResult()

    // Representa um resultado de falha, carregando uma mensagem para o usuário.
    // É uma sealed class aninhada para agrupar tipos específicos de falhas.
    sealed class Failure(val message: String) : AuthResult() {
        data object UsernameTaken : Failure("Este nome de usuário já está em uso.")
        data object InvalidCredentials : Failure("Nome de usuário ou senha inválidos.")
        data class RegistrationError(val reason: String) : Failure(reason)
        data object LoginError: Failure("Usuário não encontrado.")
    }
}


/**
 * Orquestra as operações de autenticação, como registro e login.
 * Esta classe encapsula a lógica de negócio do sistema de autenticação.
 *
 * Repare que ela recebe o 'UserRepository' em seu construtor. Isso é Injeção de Dependência.
 * O AuthService não cria sua própria dependência; ele a recebe de fora. Isso o torna
 * extremamente testável e desacoplado, como discutimos anteriormente.
 *
 * @param userRepository A instância do repositório de usuários que será utilizada para
 *                       acessar os dados.
 */
class AuthService(private val userRepository: UserRepository) {

    /**
     * Tenta registrar um novo usuário no sistema.
     *
     * @param username O nome de usuário desejado.
     * @param password A senha em texto puro fornecida pelo usuário.
     * @return Um [AuthResult] que pode ser [AuthResult.Success] ou um dos tipos
     *         de [AuthResult.Failure].
     */
    fun register(username: String, password: String): AuthResult {
        // 1. Validação dos Dados de Entrada (Regras de Negócio)
        if (username.isBlank() || password.isBlank()) {
            return AuthResult.Failure.RegistrationError("Nome de usuário e senha não podem ser vazios.")
        }
        if (password.length < 4) { // Exemplo de regra de negócio: senha mínima.
            return AuthResult.Failure.RegistrationError("A senha deve ter no mínimo 4 caracteres.")
        }

        // 2. Verifica se o usuário já existe, utilizando o repositório.
        if (userRepository.existsByUsername(username)) {
            return AuthResult.Failure.UsernameTaken
        }

        // 3. Hashing da Senha - PASSO DE SEGURANÇA CRÍTICO
        // Nós nunca salvamos a senha real. Usamos BCrypt para gerar um "hash" seguro dela.
        // O segundo parâmetro, 'gensalt()', gera o "sal", uma string aleatória que torna o
        // hash único para cada senha, protegendo contra ataques de rainbow table.
        val hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt())

        // 4. Criação da nova entidade User.
        val newUser = User(
            id = UUID.randomUUID(), // Gera um ID único para o novo usuário.
            username = username,
            hashedPassword = hashedPassword // Armazena a senha HASHEADA.
        )

        // 5. Salva o novo usuário através do repositório.
        userRepository.save(newUser)

        // 6. Retorna o sucesso, com o objeto User criado.
        return AuthResult.Success(newUser)
    }

    /**
     * Tenta autenticar um usuário com as credenciais fornecidas.
     *
     * @param username O nome de usuário para login.
     * @param password A senha em texto puro para verificação.
     * @return Um [AuthResult] de sucesso com o usuário logado, ou falha.
     */
    fun login(username: String, password: String): AuthResult {
        // 1. Busca o usuário no repositório.
        val user = userRepository.findByUsername(username)
            ?: return AuthResult.Failure.LoginError // Se o usuário não existe, falha imediatamente.

        // 2. Compara a senha fornecida com o hash armazenado - PASSO DE SEGURANÇA CRÍTICO
        // A função 'BCrypt.checkpw' refaz o hash da senha fornecida (usando o mesmo "sal" que está
        // embutido no 'hashedPassword') e compara os resultados. Retorna true se corresponder.
        if (BCrypt.checkpw(password, user.hashedPassword)) {
            // As senhas correspondem, o login é bem-sucedido.
            return AuthResult.Success(user)
        } else {
            // As senhas não correspondem.
            return AuthResult.Failure.InvalidCredentials
        }
    }
}
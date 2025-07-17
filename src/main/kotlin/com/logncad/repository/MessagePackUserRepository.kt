// 2025
// By Pedro henrique garcia.
// Github/gitlab: Phkaiser13

package com.logncad.repository

import com.logncad.model.User
import org.msgpack.core.MessagePack
import java.io.File
import java.io.IOException
import java.util.UUID

/**
 * Uma implementação concreta da interface [UserRepository] que persiste os dados
 * dos usuários em um arquivo local usando o formato de serialização binária MessagePack.
 *
 * Esta classe utiliza uma estratégia de cache em memória para otimizar a performance.
 * Na inicialização, ela carrega todos os usuários do arquivo para um mapa em memória.
 * Operações de leitura (`find`, `exists`) são extremamente rápidas, pois acessam este mapa.
 * A única operação de escrita no disco ocorre durante o método `save`.
 */
class MessagePackUserRepository : UserRepository {

    // companion object é o local em Kotlin para declarar constantes e métodos
    // estáticos, similar ao 'static' do Java.
    companion object {
        // Define o nome do nosso "banco de dados" em arquivo.
        // É uma boa prática ter isso como uma constante para evitar erros de digitação.
        private const val DB_FILE = "users.msgpack"
    }

    // O cache em memória. Usamos um MutableMap com o username como chave para buscas O(1)
    // (extremamente rápidas). A chave (String) é o username, e o valor (User) é o objeto completo.
    private val usersByUsername: MutableMap<String, User>

    // O bloco 'init' é executado quando uma instância desta classe é criada.
    // É o lugar perfeito para carregar os dados do arquivo e popular nosso cache em memória.
    init {
        // Carrega a lista de usuários do arquivo e a converte em um mapa.
        // A função associateBy do Kotlin é ótima para transformar uma lista de objetos
        // em um mapa, usando uma propriedade do objeto como chave.
        usersByUsername = loadUsersFromFile().associateBy { it.username }.toMutableMap()
    }

    /**
     * Implementação do método da interface.
     * Salva o usuário no cache em memória e depois persiste o estado completo
     * do cache no arquivo MessagePack.
     */
    override fun save(user: User) {
        usersByUsername[user.username] = user
        persistCacheToFile()
    }

    /**
     * Implementação do método da interface.
     * Busca o usuário diretamente do nosso mapa em memória. Esta operação é muito rápida.
     */
    override fun findByUsername(username: String): User? {
        return usersByUsername[username]
    }

    /**
     * Implementação do método da interface.
     * Verifica a existência da chave no mapa, o que também é uma operação muito rápida.
     */
    override fun existsByUsername(username: String): Boolean {
        return usersByUsername.containsKey(username)
    }


    /**
     * Implementação do método da interface.
     * Retorna todos os valores (os objetos User) do nosso mapa como uma lista.
     */
    override fun findAll(): List<User> {
        return usersByUsername.values.toList()
    }


    /**
     * Orquestra a serialização de todos os usuários do cache para o arquivo.
     * A anotação @Synchronized garante que, mesmo em um ambiente com múltiplas threads,
     * apenas uma thread possa escrever no arquivo por vez, prevenindo corrupção de dados.
     */
    @Synchronized
    private fun persistCacheToFile() {
        val file = File(DB_FILE)
        try {
            // Usa um packer de buffer, que é eficiente para construir o pacote em memória
            // antes de escrevê-lo no disco de uma só vez.
            val packer = MessagePack.newDefaultBufferPacker()

            // A estrutura do nosso arquivo será um único array contendo todos os usuários.
            val users = findAll()
            packer.packArrayHeader(users.size)
            for (user in users) {
                // Cada usuário, por sua vez, é um array de 3 elementos: [id, username, hashedPassword]
                packer.packArrayHeader(3)
                // O UUID é convertido para String para ser salvo.
                packer.packString(user.id.toString())
                packer.packString(user.username)
                packer.packString(user.hashedPassword)
            }
            packer.close()

            // Escreve os bytes serializados para o arquivo, substituindo o conteúdo anterior.
            file.writeBytes(packer.toByteArray())

        } catch (e: IOException) {
            // Em uma aplicação real, aqui seria o lugar para logar o erro de forma apropriada.
            println("Erro ao persistir dados no arquivo: ${e.message}")
        }
    }


    /**
     * Lê e desserializa o arquivo 'users.msgpack', convertendo o conteúdo binário
     * de volta para uma lista de objetos User.
     *
     * @return A lista de usuários lida do arquivo, ou uma lista vazia se o arquivo não
     *         existir (ex: na primeira execução) ou ocorrer um erro.
     */
    private fun loadUsersFromFile(): List<User> {
        val file = File(DB_FILE)
        if (!file.exists()) {
            return emptyList() // Se o arquivo não existe, não há usuários para carregar.
        }

        return try {
            val bytes = file.readBytes()
            val unpacker = MessagePack.newDefaultUnpacker(bytes)
            val users = mutableListOf<User>()

            // Lê o header do array principal para saber quantos usuários esperamos.
            val userCount = unpacker.unpackArrayHeader()
            repeat(userCount) {
                // Lê o header do array de cada usuário.
                unpacker.unpackArrayHeader() // Espera-se que seja 3.
                // Lê os dados na mesma ordem em que foram escritos.
                val id = UUID.fromString(unpacker.unpackString())
                val username = unpacker.unpackString()
                val hashedPassword = unpacker.unpackString()
                users.add(User(id, username, hashedPassword))
            }
            unpacker.close()
            users

        } catch (e: IOException) {
            println("Erro ao carregar dados do arquivo: ${e.message}. Retornando lista vazia.")
            emptyList()
        }
    }
}
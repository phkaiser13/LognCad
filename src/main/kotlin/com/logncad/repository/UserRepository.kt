// 2025
// By Pedro henrique garcia.
// Github/gitlab: Phkaiser13

// Estamos agora no pacote 'repository', dedicado às classes que interagem com a fonte
// de dados (seja um arquivo, um banco de dados, ou uma API externa).
package com.logncad.repository

import com.logncad.model.User

/**
 * Define um "Contrato de Repositório" para a entidade User.
 *
 * Utilizar uma interface aqui é um dos pilares da arquitetura limpa e dos princípios SOLID
 * (especificamente, o 'D' de Inversão de Dependência). Em vez de nossas classes de serviço
 * dependerem de uma implementação concreta (como uma classe que salva em MessagePack),
 * elas dependerão desta ABSTRAÇÃO (a interface).
 *
 * Vantagens:
 * 1. **Desacoplamento:** Podemos trocar a forma de armazenamento (de MessagePack para um banco de
 *    dados SQL, por exemplo) sem alterar NADA nas classes de serviço. Só precisaríamos
 *    criar uma nova classe que implementa esta interface.
 * 2. **Testabilidade:** Em nossos testes, podemos criar uma implementação "fake" em memória
 *    (um "mock") desta interface, permitindo testar a lógica de negócio de forma
 *    rápida e isolada, sem tocar no sistema de arquivos real.
 */
interface UserRepository {

    /**
     * Persiste um novo usuário na fonte de dados.
     * Se um usuário com o mesmo ID já existir, ele deve ser atualizado.
     *
     * @param user O objeto User a ser salvo.
     */
    fun save(user: User)

    /**
     * Busca por um usuário específico pelo seu nome de usuário.
     *
     * @param username O nome de usuário a ser procurado.
     * @return O objeto [User] correspondente se encontrado, ou `null` caso contrário.
     *         Retornar um tipo anulável (User?) é a forma idiomática do Kotlin de lidar
     *         com a possibilidade de algo não ser encontrado.
     */
    fun findByUsername(username: String): User?

    /**
     * Verifica de forma eficiente se um usuário com o nome de usuário especificado já existe.
     * Isso é geralmente mais performático do que chamar `findByUsername` e verificar se é nulo,
     * pois pode não precisar carregar o objeto inteiro da fonte de dados.
     *
     * @param username O nome de usuário a ser verificado.
     * @return `true` se o usuário existir, `false` caso contrário.
     */
    fun existsByUsername(username: String): Boolean

    /**
     * Retorna todos os usuários cadastrados no sistema.
     * Útil para carregar todos os dados na inicialização da aplicação.
     *
     * @return Uma lista (`List<User>`) contendo todos os usuários. Retorna uma lista
     *         vazia se não houver nenhum usuário.
     */
    fun findAll(): List<User>
}
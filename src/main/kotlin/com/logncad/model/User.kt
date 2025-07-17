// 2025
// By Pedro henrique garcia.
// Github/gitlab: Phkaiser13

// A declaração de pacote define um "namespace" para nossas classes, evitando conflitos
// e organizando o código de forma lógica. Nosso modelo de usuário pertence ao pacote 'model'.
package com.logncad.model

import java.util.UUID

/**
 * Representa a entidade 'Usuário' em nosso sistema.
 *
 * Utilizar uma 'data class' é a forma idiomática do Kotlin para criar classes que são
 * primariamente recipientes de dados. O compilador Kotlin gera automaticamente os métodos
 * padrão: equals(), hashCode(), toString(), componentN() e copy(), o que nos poupa de
 * escrever código repetitivo e propenso a erros.
 *
 * A imutabilidade (uso de 'val' em vez de 'var') é um princípio de design fundamental
 * para criar sistemas mais seguros e fáceis de entender. Uma vez que um objeto User é criado,
 * seus dados não podem ser alterados, prevenindo efeitos colaterais inesperados.
 *
 * @property id O identificador único universal (UUID) para o usuário. Garante uma chave
 *              primária única e segura, independente do nome de usuário que pode,
 *              hipoteticamente, mudar.
 * @property username O nome de usuário escolhido pelo usuário. Utilizado para o processo de login.
 * @property hashedPassword A senha do usuário, já processada por um algoritmo de hash seguro
 *                        (como o bcrypt). **NUNCA, JAMAIS, armazene a senha em texto puro aqui.**
 *                        O nome da propriedade deixa claro o seu conteúdo esperado.
 */
data class User(
    val id: UUID,
    val username: String,
    val hashedPassword: String
)
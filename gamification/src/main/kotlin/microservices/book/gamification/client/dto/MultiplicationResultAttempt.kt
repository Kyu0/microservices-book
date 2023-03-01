package microservices.book.gamification.client.dto

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import microservices.book.gamification.client.MultiplicationResultAttemptDeserializer

/**
 * 유저가 곱셈을 푼 답안을 정의한 클래스
 */
@JsonDeserialize(using = MultiplicationResultAttemptDeserializer::class)
data class MultiplicationResultAttempt(
    val userAlias: String?,
    val multiplicationFactorA: Int,
    val multiplicationFactorB: Int,
    val resultAttempt: Int,
    val correct: Boolean
) {

    constructor() : this(null, -1, -1, -1, false)
}
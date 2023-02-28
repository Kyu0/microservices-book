package microservies.book.gamification.event

import java.io.Serializable

/**
 * 시스템에서 {@link microservices.book.multiplication.domain.Multiplication}
 * 문제가 해결됐다는 사실을 모델링한 이벤트
 * 곱셈에 대한 컨텍스트 정보를 제공
 */
class MultiplicationSolvedEvent(
    val multiplicationResultAttemptId: Long,
    val userId: Long,
    val correct: Boolean
): Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MultiplicationSolvedEvent

        if (multiplicationResultAttemptId != other.multiplicationResultAttemptId) return false
        if (userId != other.userId) return false
        if (correct != other.correct) return false

        return true
    }

    override fun hashCode(): Int {
        var result = multiplicationResultAttemptId.hashCode()
        result = 31 * result + userId.hashCode()
        result = 31 * result + correct.hashCode()
        return result
    }

    override fun toString(): String {
        return "MultiplicationSolvedEvent(multiplicationResultAttemptId=$multiplicationResultAttemptId, userId=$userId, correct=$correct)"
    }
}
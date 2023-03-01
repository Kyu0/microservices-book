package microservices.book.gamification.domain

import jakarta.persistence.*

/**
 * 점수와 답안을 연결하는 클래스
 * 사용자와 점수가 등록된 시간의 타임스탬프를 포함
 */
@Entity
data class ScoreCard(
    @Column(name = "USER_ID") val userId: Long,
    @Column(name = "ATTEMPT_ID") val attemptId: Long,
    val score: Int
) {
    @Id @GeneratedValue @Column(name = "CARD_ID")
    val cardId: Long? = null

    @Column(name = "SCORE_TIMESTAMP")
    val scoreTimestamp: Long = System.currentTimeMillis()

    constructor(userId: Long, attemptId: Long): this(userId, attemptId, DEFAULT_SCORE)

    // 명시되지 않은 경우 이 카드에 할당되는 기본 점수
    companion object {
        const val DEFAULT_SCORE = 10
    }
}
package microservices.book.multiplication.domain

import jakarta.persistence.*

/**
 * {@link User}가 {@link Multiplication}을 푸는 시도를 정의한 클래스
 */
@Entity
data class MultiplicationResultAttempt(
    @ManyToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "USER_ID")
    val user: User,

    @ManyToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "MULTIPLICATION_ID")
    val multiplication: Multiplication,

    val resultAttempt: Int,
    val correct: Boolean
) {
    @Id @GeneratedValue
    var id: Long? = null
    override fun toString(): String {
        return "MultiplicationResultAttempt(id=$id, user_id=${user.id}, multiplication_id=${multiplication.id}, resultAttempt=$resultAttempt, correct=$correct)"
    }


}
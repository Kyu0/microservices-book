package microservices.book.multiplication.service

import microservices.book.multiplication.domain.Multiplication
import microservices.book.multiplication.domain.MultiplicationResultAttempt

interface MultiplicationService {

    /**
     * 두 개의 무작위 인수를 담은 {@link Multiplication} 객체를 생성한다.
     * 무작위로 생성되는 숫자의 범위는 11~99
     *
     * @return 무작위 인수를 담은 {@link Multiplication} 객체
     */
    fun createRandomMultiplication(): Multiplication

    /**
     * @return 곱셈 계산 결과가 맞으면 true, 아니면 false
     */
    fun checkAttempt(resultAttempt: MultiplicationResultAttempt): Boolean

    /**
     * @return 유저의 최근 제출한 {@link MultiplicationResultAttempt}을 반환한다.
     */
    fun getStatsForUser(userAlias: String): List<MultiplicationResultAttempt>

    /**
     * @return 해당 id를 가진 {@link MultiplicationResultAttempt}를 반환한다.
     */
    fun findById(id: Long): MultiplicationResultAttempt?
}
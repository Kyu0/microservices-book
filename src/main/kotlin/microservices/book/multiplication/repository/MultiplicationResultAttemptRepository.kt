package microservices.book.multiplication.repository

import microservices.book.multiplication.domain.MultiplicationResultAttempt
import org.springframework.data.repository.CrudRepository

/**
 * 답안을 저장하고 조회하기 위한 인터페이스
 */
interface MultiplicationResultAttemptRepository : CrudRepository<MultiplicationResultAttempt, Long> {

    /**
     * @return 닉네임에 해당하는 사용자의 최근 답안 5개
     */
    fun findTop5ByUserAliasOrderByIdDesc(userAlias: String): List<MultiplicationResultAttempt>
}
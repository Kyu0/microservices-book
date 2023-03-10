package microservices.book.multiplication.repository

import microservices.book.multiplication.domain.User
import org.springframework.data.repository.CrudRepository

/**
 * {@link User}를 저장하고 조회하기 위한 인터페이스
 */
interface UserRepository: CrudRepository<User, Long> {

    fun findByAlias(alias: String): User?
}
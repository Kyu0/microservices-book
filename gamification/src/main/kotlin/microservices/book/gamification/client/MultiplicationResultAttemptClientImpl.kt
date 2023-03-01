package microservices.book.gamification.client

import microservices.book.gamification.client.dto.MultiplicationResultAttempt
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject

/**
 * Multiplication 마이크로서비스와 REST로 연결하기 위한
 * MultiplicationResultAttemptClient 인터페이스의 구현체
 */
@Component
class MultiplicationResultAttemptClientImpl(
    private val restTemplate: RestTemplate,
    @Value("\${multiplicationHost}")
    private val multiplicationHost: String
): MultiplicationResultAttemptClient {

    override fun retrieveMultiplicationResultAttemptById(multiplicationResultAttemptId: Long): microservices.book.gamification.client.dto.MultiplicationResultAttempt {
        return restTemplate.getForObject(
            "${multiplicationHost}/results/${multiplicationResultAttemptId}", MultiplicationResultAttempt::class
        )
    }
}
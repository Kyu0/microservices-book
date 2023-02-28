package microservices.book.multiplication.event

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

/**
 * 이벤트 버스와의 통신을 처리
 */
@Component
class EventDispatcher(
    @Autowired
    val rabbitTemplate: RabbitTemplate,
    @Value("\${multiplication.exchange}")
    val multiplicationExchange: String, // Multiplication 관련 정보를 전달하기 위한 익스체인지
    @Value("\${multiplication.solved.key}")
    val multiplicationSolvedRoutingKey: String
) {
    fun send(multiplicationSolvedEvent: MultiplicationSolvedEvent) {
        rabbitTemplate.convertAndSend(
            multiplicationExchange,
            multiplicationSolvedRoutingKey,
            multiplicationSolvedEvent
        )
    }
}
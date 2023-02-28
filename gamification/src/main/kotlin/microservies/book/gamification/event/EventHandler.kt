package microservies.book.gamification.event

import microservies.book.gamification.service.GameService
import org.slf4j.LoggerFactory
import org.springframework.amqp.AmqpRejectAndDontRequeueException
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class EventHandler(
    @Autowired
    private val gameService: GameService
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @RabbitListener(queues = ["\${multiplication.queue}"])
    fun handleMultiplicationSolved(event: MultiplicationSolvedEvent) {
        logger.info("Multiplication Solved Event 수신: $event.multiplicationResultAttemptId")

        try {
            gameService.newAttemptForUser(event.userId, event.multiplicationResultAttemptId, event.correct)
        }
        catch (e: Exception) {
            logger.error("MultiplicationSolvedEvent 처리 중 에러가 발생했습니다.", e)

            throw AmqpRejectAndDontRequeueException(e)
        }
    }
}
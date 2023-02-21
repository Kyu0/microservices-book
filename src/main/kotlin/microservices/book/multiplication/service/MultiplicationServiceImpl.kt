package microservices.book.multiplication.service

import microservices.book.multiplication.domain.Multiplication
import microservices.book.multiplication.domain.MultiplicationResultAttempt
import microservices.book.multiplication.domain.User
import microservices.book.multiplication.event.EventDispatcher
import microservices.book.multiplication.event.MultiplicationSolvedEvent
import microservices.book.multiplication.repository.MultiplicationResultAttemptRepository
import microservices.book.multiplication.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MultiplicationServiceImpl(
    @Autowired
    private val randomGeneratorService: RandomGeneratorService,
    @Autowired
    private val attemptRepository: MultiplicationResultAttemptRepository,
    @Autowired
    private val userRepository: UserRepository,
    @Autowired
    private val eventDispatcher: EventDispatcher
): MultiplicationService {

    override fun createRandomMultiplication(): Multiplication {
        val factorA:Int = randomGeneratorService.generateRandomFactor()
        val factorB:Int = randomGeneratorService.generateRandomFactor()

        return Multiplication(factorA, factorB)
    }

    @Transactional
    override fun checkAttempt(attempt: MultiplicationResultAttempt): Boolean {
        val user: User? = userRepository.findByAlias(attempt.user.alias)

        check(!attempt.correct) {
            "채점한 상태로 보낼 수 없습니다."
        }

        val isCorrect = attempt.resultAttempt ==
                attempt.multiplication.factorA * attempt.multiplication.factorB

        val checkedAttempt = MultiplicationResultAttempt(user ?: attempt.user, attempt.multiplication, attempt.resultAttempt, isCorrect)

        attemptRepository.save(checkedAttempt)

        // 이벤트로 결과를 전송
        eventDispatcher.send(MultiplicationSolvedEvent(
            checkedAttempt.id!!,
            checkedAttempt.user.id!!,
            checkedAttempt.correct
        ))

        return isCorrect
    }

    override fun getStatsForUser(userAlias: String): List<MultiplicationResultAttempt> {
        return attemptRepository.findTop5ByUserAliasOrderByIdDesc(userAlias)
    }
}
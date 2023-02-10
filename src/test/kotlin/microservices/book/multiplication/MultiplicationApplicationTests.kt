package microservices.book.multiplication

import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.spec.style.Test
import io.kotest.matchers.shouldBe
import io.mockk.*

import microservices.book.multiplication.domain.Multiplication
import microservices.book.multiplication.repository.MultiplicationResultAttemptRepository
import microservices.book.multiplication.repository.UserRepository
import microservices.book.multiplication.service.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

class RandomMultiplicationTest(
    @MockkBean
    val randomGeneratorService: RandomGeneratorService,

    @MockkBean
    val multiplicationResultAttemptRepository: MultiplicationResultAttemptRepository,

    @MockkBean
    val userRepository: UserRepository
): BehaviorSpec() {

    @Autowired
    val multiplicationService:MultiplicationService = MultiplicationServiceImpl(randomGeneratorService, multiplicationResultAttemptRepository, userRepository)

    init {
        given("generatorRandomFactor() 메소드가 순차적으로 50, 30을 반환") {
            every { randomGeneratorService.generateRandomFactor() } returnsMany listOf(50, 30)
            val multiplication: Multiplication = multiplicationService.createRandomMultiplication()
            `when`("factorA는") {
                then("50이다.") {
                    multiplication.factorA shouldBe 50
                }
            }

            `when`("factorB는") {
                then("30이다.") {
                    multiplication.factorB shouldBe 30
                }
            }

            `when`("factorA * factorB는") {
                then("1500이다.") {
                    multiplication.result shouldBe 1500
                }
            }
        }
    }
}
package microservices.book.multiplication

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.spec.style.Test
import io.kotest.matchers.shouldBe
import io.mockk.*

import microservices.book.multiplication.domain.Multiplication
import microservices.book.multiplication.service.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

class RandomMultiplicationTest: BehaviorSpec() {
    val randomGeneratorService:RandomGeneratorService = mockk<RandomGeneratorService>()
    @Autowired
    val multiplicationService:MultiplicationService = MultiplicationServiceImpl(randomGeneratorService)

    fun `예제 테스트`() {
        given("generatorRandomFactor() 메소드가 순차적으로 50, 30을 반환") {
            every { randomGeneratorService.generateRandomFactor() } returnsMany listOf(50, 30)
            val multiplication: Multiplication = multiplicationService.createRandomMultiplication()
            `when`("factorA는") {
                then("50이다.") {
                    multiplication.getFactorA() shouldBe 50
                }
            }

            `when`("factorB는") {
                then("30이다.") {
                    multiplication.getFactorB() shouldBe 30
                }
            }

            `when`("factorA * factorB는") {
                then("1500이다.") {
                    multiplication.getResult() shouldBe 1500
                }
            }
        }
    }
}
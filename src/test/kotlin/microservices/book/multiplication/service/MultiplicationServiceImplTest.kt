package microservices.book.multiplication.service

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import microservices.book.multiplication.domain.Multiplication
import microservices.book.multiplication.domain.MultiplicationResultAttempt
import microservices.book.multiplication.domain.User

class MultiplicationServiceImplTest : BehaviorSpec() {

    private val randomGeneratorService: RandomGeneratorService = mockk<RandomGeneratorService>()
    private val multiplicationServiceImpl: MultiplicationServiceImpl = MultiplicationServiceImpl(randomGeneratorService)

    init {
        given("generateRandomFactor() 메소드가 순서대로 50, 30을 반환") {
            every { randomGeneratorService.generateRandomFactor() } returnsMany listOf(50, 30)
            val multiplication: Multiplication = multiplicationServiceImpl.createRandomMultiplication()

            `when`("FactorA는") {
                then("50이다.") {
                    multiplication.factorA shouldBe 50
                }
            }
            `when`("FactorB는") {
                then("30이다.") {
                    multiplication.factorB shouldBe 30
                }
            }
            `when`("Result는") {
                then("1500이다.") {
                    multiplication.result shouldBe 1500
                }
            }
        }

        given("계산 결과(50 * 60 = 3000)가 맞는지 확인하는 테스트") {
            val multiplication: Multiplication = Multiplication(50, 60)
            val user: User = User("John_Doe")
            val attempt: MultiplicationResultAttempt = MultiplicationResultAttempt(user, multiplication, 3000)

            `when`("checkAttempt() 실행 결과가") {
                val attemptResult : Boolean = multiplicationServiceImpl.checkAttempt(attempt)
                then("true다.") {
                    attemptResult shouldBe true
                }
            }

        }

        given("계산 결과(50 * 60 = 3010)가 틀린지 확인하는 테스트") {
            val multiplication: Multiplication = Multiplication(50, 60)
            val user: User = User("John Doe")
            val attempt: MultiplicationResultAttempt = MultiplicationResultAttempt(user, multiplication, 3010)

            `when`("checkAttempt() 실행 결과가") {
                val attemptResult: Boolean = multiplicationServiceImpl.checkAttempt(attempt)
                then("false다.") {
                    attemptResult shouldBe false
                }
            }
        }
    }
}
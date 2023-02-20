package microservices.book.multiplication.service

import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import microservices.book.multiplication.domain.Multiplication
import microservices.book.multiplication.domain.MultiplicationResultAttempt
import microservices.book.multiplication.domain.User
import microservices.book.multiplication.repository.MultiplicationResultAttemptRepository
import microservices.book.multiplication.repository.UserRepository
import org.mockito.internal.matchers.Null
import java.util.OptionalLong

class MultiplicationServiceImplTest(
    @MockkBean
    val randomGeneratorService: RandomGeneratorService,

    @MockkBean
    val attemptRepository: MultiplicationResultAttemptRepository,

    @MockkBean
    val userRepository: UserRepository,

    private val multiplicationServiceImpl: MultiplicationServiceImpl = MultiplicationServiceImpl(randomGeneratorService, attemptRepository, userRepository)
) : BehaviorSpec() {
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
            val multiplication = Multiplication(50, 60)
            val user = User("John_Doe")
            val attempt = MultiplicationResultAttempt(user, multiplication, 3000, false)
            val verifiedAttempt = MultiplicationResultAttempt(user, multiplication, 3000, true)

            every { userRepository.findByAlias("John_Doe") } returns null
            every { attemptRepository.save(verifiedAttempt) } returns verifiedAttempt

            `when`("checkAttempt() 실행 결과가") {
                val attemptResult = multiplicationServiceImpl.checkAttempt(attempt)
                then("true다.") {
                    attemptResult shouldBe true
                    verify { attemptRepository.save(verifiedAttempt) }
                }
            }

        }

        given("계산 결과(50 * 60 = 3010)가 틀린지 확인하는 테스트") {
            val multiplication = Multiplication(50, 60)
            val user = User("John_Doe")
            val attempt = MultiplicationResultAttempt(user, multiplication, 3010, false)

            every { userRepository.findByAlias("John_Doe") } returns null
            every { attemptRepository.save(attempt) } returns attempt

            `when`("checkAttempt() 실행 결과가") {
                val attemptResult = multiplicationServiceImpl.checkAttempt(attempt)
                then("false다.") {
                    attemptResult shouldBe false
                    verify { attemptRepository.save(attempt) }
                }
            }
        }

        given("유저의 최근 제출 답안을 확인하는 테스트") {
            val multiplication = Multiplication(50, 60)
            val user = User("John_Doe")
            val attempt1 = MultiplicationResultAttempt(user, multiplication, 3010, false)
            val attempt2 = MultiplicationResultAttempt(user, multiplication, 3051, false)

            val latestAttempts: List<MultiplicationResultAttempt> = arrayListOf(attempt1, attempt2)

            every { userRepository.findByAlias("John_Doe") } returns null
            every { attemptRepository.findTop5ByUserAliasOrderByIdDesc("John_Doe") } returns latestAttempts

            `when`("유저의 이름을 입력하면") {
                val latestAttemptsResult = multiplicationServiceImpl.getStatsForUser("John_Doe")
                then("최근 제출 답안을 반환한다.") {
                    latestAttemptsResult shouldBe  latestAttempts
                }
            }


        }
    }
}
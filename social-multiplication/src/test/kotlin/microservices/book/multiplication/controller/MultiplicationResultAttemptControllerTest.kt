package microservices.book.multiplication.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import microservices.book.multiplication.domain.MultiplicationResultAttempt
import microservices.book.multiplication.service.MultiplicationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.json.JacksonTester
import org.springframework.test.web.servlet.MockMvc
import microservices.book.multiplication.domain.Multiplication
import microservices.book.multiplication.domain.User
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

@WebMvcTest(MultiplicationResultAttemptController::class)
class MultiplicationResultAttemptControllerTest(
    @MockkBean
    private val multiplicationService: MultiplicationService,
    @Autowired
    private val mvc: MockMvc
): BehaviorSpec() {

    private lateinit var jsonResult: JacksonTester<MultiplicationResultAttempt>
    private lateinit var jsonResultList: JacksonTester<List<MultiplicationResultAttempt>>

    init {
        JacksonTester.initFields(this, ObjectMapper())

        given("계산 결과를 잘 응답하는지 확인하는 테스트") {
            every { multiplicationService.checkAttempt(any<MultiplicationResultAttempt>()) }.returnsMany(true, false)

            `when`("계산 결과가 맞을 경우") {
                val correct = true
                val attempt = MultiplicationResultAttempt(user, multiplication, 3500, correct)
                val response = getResponse(attempt)

                then("true를 반환한다.") {
                    checkResponse(attempt, response)
                }
            }

            `when`("계산 결과가 틀릴 경우") {
                val correct = false
                val attempt = MultiplicationResultAttempt(user, multiplication, 3500, correct)
                val response = getResponse(attempt)

                then("false를 반환한다.") {
                    checkResponse(attempt, response)
                }
            }
        }

        given("유저의 최근 제출한 답안을 반환하는지 확인하는 테스트") {
            val user = User("John_Doe")
            val multiplication = Multiplication(50, 70)
            val attempt = MultiplicationResultAttempt(user, multiplication, 3500, true)
            val recentAttempts = arrayListOf(attempt, attempt)

            every { multiplicationService.getStatsForUser("John_Doe") } returns recentAttempts

            `when`("API 요청이 들어오면") {
                val response = mvc.perform(get("/results")
                    .param("alias", "John_Doe"))
                .andReturn().response

                then("유저의 최근 답안을 반환한다.") {
                    response.status shouldBe HttpStatus.OK.value()
                    response.contentAsString shouldBe jsonResultList.write(recentAttempts).json
                }
            }
        }
    }

    private fun checkResponse(expected: MultiplicationResultAttempt, response: MockHttpServletResponse) {
        response.status shouldBe HttpStatus.OK.value()
        response.contentAsString shouldBe jsonResult.write(expected).json
    }

    private fun getResponse(data: MultiplicationResultAttempt): MockHttpServletResponse {
        return mvc.perform(post("/results")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonResult.write(data).json))
            .andReturn()
            .response
    }

    companion object {
        private val user = User("John Doe")
        private val multiplication = Multiplication(50, 70)
    }
}
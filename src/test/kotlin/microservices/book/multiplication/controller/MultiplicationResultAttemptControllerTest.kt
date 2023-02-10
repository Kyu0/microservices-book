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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

@WebMvcTest(MultiplicationResultAttemptController::class)
class MultiplicationResultAttemptControllerTest(
    @MockkBean
    private val multiplicationService: MultiplicationService,
    @Autowired
    private val mvc: MockMvc
): BehaviorSpec() {

    private lateinit var jsonResult: JacksonTester<MultiplicationResultAttempt>

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
package microservices.book.multiplication.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import microservices.book.multiplication.domain.Multiplication
import microservices.book.multiplication.service.MultiplicationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.json.JacksonTester
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

@WebMvcTest(MultiplicationController::class)
class MultiplicationControllerTest(
    @MockkBean
    private val multiplicationService: MultiplicationService,
    @Autowired
    private val mvc: MockMvc
): BehaviorSpec() {

    private lateinit var json: JacksonTester<Multiplication>

//    @MockkBean
//    private lateinit var multiplicationService: MultiplicationService
//
//    @Autowired
//    private lateinit var mvc: MockMvc

    init {
        JacksonTester.initFields(this, ObjectMapper())

        given("get /multiplications/random 테스트")
        {
            `when`("인수가 70, 20으로 주어졌을 때") {
                every { multiplicationService.createRandomMultiplication() } returns Multiplication(70, 20)

                val response: MockHttpServletResponse = mvc.perform(
                    get("/multiplications/random")
                        .accept(MediaType.APPLICATION_JSON)
                )
                    .andReturn().response
                then("응답으로 받은 Multiplication의 인수들이 70, 20 이다.") {
                    response.status shouldBe HttpStatus.OK.value()
                    response.contentAsString shouldBe json.write(Multiplication(70, 20)).json
                }
            }
        }
    }
}
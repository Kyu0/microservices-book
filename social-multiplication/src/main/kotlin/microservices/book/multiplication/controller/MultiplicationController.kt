package microservices.book.multiplication.controller

import microservices.book.multiplication.domain.Multiplication
import microservices.book.multiplication.service.MultiplicationService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MultiplicationController (
    private val multiplicationService: MultiplicationService,
    @Value("\${server.port}")
    private val serverPort: Int
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    @GetMapping("/multiplications/random")
    fun getRandomMultiplication(): Multiplication {
        log.info("무작위 곱셈이 생성된 서버 @{}", serverPort)
        return multiplicationService.createRandomMultiplication()
    }
}
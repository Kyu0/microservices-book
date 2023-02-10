package microservices.book.multiplication.controller

import microservices.book.multiplication.domain.Multiplication
import microservices.book.multiplication.service.MultiplicationService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MultiplicationController (
    private val multiplicationService: MultiplicationService
) {

    @GetMapping("/multiplications/random")
    fun getRandomMultiplication(): Multiplication {
        return multiplicationService.createRandomMultiplication()
    }
}
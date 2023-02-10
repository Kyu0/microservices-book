package microservices.book.multiplication.controller

import microservices.book.multiplication.domain.MultiplicationResultAttempt
import microservices.book.multiplication.service.MultiplicationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MultiplicationResultAttemptController(
    val multiplicationService: MultiplicationService
) {

    @PostMapping("/results")
    fun postResult(@RequestBody multiplicationResultAttempt: MultiplicationResultAttempt): ResponseEntity<ResultResponse> {
        return ResponseEntity.ok(
            ResultResponse(multiplicationService.checkAttempt(multiplicationResultAttempt))
        )
    }
    class ResultResponse(
        private val correct: Boolean
    )
}
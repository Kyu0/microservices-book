package microservices.book.multiplication.controller

import microservices.book.multiplication.domain.MultiplicationResultAttempt
import microservices.book.multiplication.service.MultiplicationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class MultiplicationResultAttemptController(
    val multiplicationService: MultiplicationService
) {

    @PostMapping("/results")
    fun postResult(@RequestBody multiplicationResultAttempt: MultiplicationResultAttempt): ResponseEntity<MultiplicationResultAttempt> {
        val isCorrect = multiplicationService.checkAttempt(multiplicationResultAttempt)
        val attemptCopy = MultiplicationResultAttempt(
            user= multiplicationResultAttempt.user,
            multiplication= multiplicationResultAttempt.multiplication,
            resultAttempt = multiplicationResultAttempt.resultAttempt,
            correct = isCorrect)

        return ResponseEntity.ok(attemptCopy)
    }

    @GetMapping("/results")
    fun getStatistics(@RequestParam("alias") alias: String): ResponseEntity<List<MultiplicationResultAttempt>> {
        return ResponseEntity.ok(multiplicationService.getStatsForUser(alias))
    }
}
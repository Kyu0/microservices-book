package microservices.book.gateway.controller

import org.apache.commons.logging.LogFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class FallBackController {
    private val log = LogFactory.getLog(this::class.java)

    @RequestMapping("/fallback")
    fun fallback(): Mono<ResponseEntity<String>> {
        log.info("Fallback")
        val responseEntity = ResponseEntity<String>(ERROR_MESSAGE, HttpStatus.BAD_GATEWAY)

        return Mono.create { responseEntity }
    }

    companion object {
        private val ERROR_MESSAGE = "API 서버의 응답이 없습니다."
    }
}
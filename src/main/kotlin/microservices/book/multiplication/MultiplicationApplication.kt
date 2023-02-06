package microservices.book.multiplication

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MultiplicationApplication

fun main(args: Array<String>) {
	runApplication<MultiplicationApplication>(*args)
}

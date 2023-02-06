package microservices.book.multiplication.service

import microservices.book.multiplication.domain.Multiplication
import org.springframework.beans.factory.annotation.Autowired

class MultiplicationServiceImpl(
    @Autowired private val randomGeneratorService: RandomGeneratorService
): MultiplicationService {

    override fun createRandomMultiplication(): Multiplication {
        val factorA:Int = randomGeneratorService.generateRandomFactor()
        val factorB:Int = randomGeneratorService.generateRandomFactor()

        return Multiplication(factorA, factorB)
    }
}
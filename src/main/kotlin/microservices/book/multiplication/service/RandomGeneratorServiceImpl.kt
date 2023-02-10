package microservices.book.multiplication.service

import org.springframework.stereotype.Service

@Service
class RandomGeneratorServiceImpl : RandomGeneratorService {
    override fun generateRandomFactor(): Int {
        return (MINIMUM_FACTOR until MAXIMUM_FACTOR + 1).random()
    }

    companion object {
        private const val MINIMUM_FACTOR = 11
        private const val MAXIMUM_FACTOR = 99
    }
}
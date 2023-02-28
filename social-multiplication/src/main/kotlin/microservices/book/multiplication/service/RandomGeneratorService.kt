package microservices.book.multiplication.service

interface RandomGeneratorService {

    /**
     * @return 무작위로 생성한 11에서 99까지의 인수
     */
    fun generateRandomFactor(): Int
}
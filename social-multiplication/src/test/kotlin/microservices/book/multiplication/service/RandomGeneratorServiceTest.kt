package microservices.book.multiplication.service

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.containAll
import io.kotest.matchers.should
import org.springframework.beans.factory.annotation.Autowired

class RandomGeneratorServiceTest: BehaviorSpec() {

    @Autowired
    private lateinit var randomGeneratorService: RandomGeneratorService

    fun `생성한 랜덤 인수가 유효 범위 내에 있는지 확인하는 테스트`() {

        given("generatorRandomFactor() 메소드 반환값이 유효 범위 내에 있는지 확인") {
            `when`("repeatCount 만큼 반복했을 때 반환값들이") {
                val repeatCount: Int = 1000
                val randomFactors: List<Int> = (0 until repeatCount)
                    .map { randomGeneratorService.generateRandomFactor() }
                then("MIN_VALUE ~ MAX_VALUE 사이의 값이다.") {
                    (MIN_VALUE until MAX_VALUE + 1).toList() should containAll(randomFactors)
                }
            }
        }
    }

    companion object {
        private const val MIN_VALUE = 11
        private const val MAX_VALUE = 99
    }
}
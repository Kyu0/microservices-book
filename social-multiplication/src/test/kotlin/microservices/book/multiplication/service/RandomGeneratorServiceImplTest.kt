package microservices.book.multiplication.service

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.containAll
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe


class RandomGeneratorServiceImplTest : BehaviorSpec(){

    private val randomGeneratorServiceImpl: RandomGeneratorServiceImpl = RandomGeneratorServiceImpl()
    init {
        given("generatorRandomFactor() 메소드 반환값이 유효 범위 내에 있는지 확인") {
            `when`("repeatCount 만큼 반복했을 때 반환값들이") {
                val repeatCount: Int = 1000
                val randomFactors: List<Int> = (0 until repeatCount)
                    .map { randomGeneratorServiceImpl.generateRandomFactor() }
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
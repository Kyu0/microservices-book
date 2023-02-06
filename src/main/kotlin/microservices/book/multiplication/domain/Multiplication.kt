package microservices.book.multiplication.domain

class Multiplication(factorA:Int, factorB:Int) {
    private val factorA:Int
    private val factorB:Int
    private val result:Int

    init {
        this.factorA = factorA
        this.factorB = factorB
        this.result = this.factorA * this.factorB
    }

    fun getFactorA():Int {
        return this.factorA
    }

    fun getFactorB():Int {
        return this.factorB
    }

    fun getResult():Int {
        return this.result
    }

    override fun toString(): String {
        return "Multiplication{" +
                "factorA=" + this.factorA +
                ", factorB=" + this.factorB +
                ", result(A*B)=" + this.result +
                "}"
    }
}
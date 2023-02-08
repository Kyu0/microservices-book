package microservices.book.multiplication.domain

class Multiplication(
    val factorA: Int
    , val factorB: Int
) {
    val result: Int = this.factorA * this.factorB


    override fun toString(): String {
        return "Multiplication{" +
                "factorA=" + this.factorA +
                ", factorB=" + this.factorB +
                ", result(A*B)=" + this.result +
                "}"
    }
}
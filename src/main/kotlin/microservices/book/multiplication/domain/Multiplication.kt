package microservices.book.multiplication.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
data class Multiplication(
    val factorA: Int,
    val factorB: Int
) {
    @Id @GeneratedValue @Column(name = "MULTIPLICATION_ID")
    var id: Long? = null

    val result: Int = this.factorA * this.factorB

    override fun toString(): String {
        return "Multiplication(id=$id, factorA=$factorA, factorB=$factorB, result=$result)"
    }
}
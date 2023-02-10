package microservices.book.multiplication.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
class User(
    val alias: String
) {
    @Id @GeneratedValue @Column(name = "USER_ID")
    var id: Long? = null
    override fun toString(): String {
        return "User(id=$id, alias='$alias')"
    }
}
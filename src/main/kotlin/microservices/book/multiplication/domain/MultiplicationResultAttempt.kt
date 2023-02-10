package microservices.book.multiplication.domain

data class MultiplicationResultAttempt(
    val user: User,
    val multiplication: Multiplication,
    val resultAttempt: Int
) {

}
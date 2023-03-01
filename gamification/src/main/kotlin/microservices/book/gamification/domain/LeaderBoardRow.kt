package microservices.book.gamification.domain

data class LeaderBoardRow(
    val userId: Long,
    val totalScore: Long
) {
    constructor() : this(0, 0)
}
package microservices.book.gamification.domain

/**
 * 한 번 혹은 여러 번의 게임 결과를 포함하는 객체
 * {@link ScoreCard} 객체와 {@link BadgeCard} 로 이뤄짐
 */
data class GameStats(
    val userId: Long,
    val score: Int,
    val badges: List<Badge>
) {

    companion object {
        /**
         * 빈 인스턴스(0점과 배지 없는 상태)를 만들기 위한 팩토리 메소드
         * @param userId 사용자 ID
         * @return {@link GameStats} 객체(0점과 배지 없는 상태)
         */
        fun emptyStats(userId: Long): GameStats {
            return GameStats(
                userId,
                0,
                mutableListOf()
            )
        }
    }
}
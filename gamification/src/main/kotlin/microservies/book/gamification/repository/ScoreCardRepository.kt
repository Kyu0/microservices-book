package microservies.book.gamification.repository

import microservies.book.gamification.domain.LeaderBoardRow
import microservies.book.gamification.domain.ScoreCard
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ScoreCardRepository: JpaRepository<ScoreCard, Long> {
    /**
     * ScoreCard의 점수를 합해서 사용자의 총 점수를 조회
     * @param userId 총 점수를 조회하고자 하는 사용자의 ID
     * @return 사용자의 총 점수
     */
    @Query("SELECT SUM(s.score)" +
            "FROM microservices.book.gamification.domain.ScoreCard s " +
            "WHERE s.userId = :userId GROUP BY s.userId")
    fun getTotalScoreForUser(@Param("userId") userId: Long): Int

    /**
     * 사용자와 사용자의 총 점수를 나타내는 {@link LeaderBoardRow} 리스트를 조회
     * @return 높은 점수 순으로 정렬된 리더보드
     */
    @Query("SELECT NEW microservices.book.gamification.domain.LeaderBoardRow(s.userId, SUM(s.score)) " +
            "FROM microservices.book.gamification.domain.ScoreCard s " +
            "GROUP BY s.userId ORDER BY SUM(s.score) DESC")
    fun findFirst10(): List<LeaderBoardRow>

    /**
     * 사용자의 모든 ScoreBoard를 조회
     * @param userId 사용자 ID
     * @return 특정 사용자의 최근 순으로 정렬된 ScoreCard 리스트
     */
    fun findByUserIdOrderByScoreTimestampDesc(userId: Long): List<ScoreCard>
}
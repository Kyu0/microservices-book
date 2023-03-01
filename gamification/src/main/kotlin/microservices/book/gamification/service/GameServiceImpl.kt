package microservices.book.gamification.service

import microservices.book.gamification.client.MultiplicationResultAttemptClient
import microservices.book.gamification.domain.Badge
import microservices.book.gamification.domain.BadgeCard
import microservices.book.gamification.domain.GameStats
import microservices.book.gamification.domain.ScoreCard
import microservices.book.gamification.repository.BadgeCardRepository
import microservices.book.gamification.repository.ScoreCardRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GameServiceImpl(
    @Autowired private val scoreCardRepository: ScoreCardRepository,
    @Autowired private val badgeCardRepository: BadgeCardRepository,
    @Autowired private val attemptClient: MultiplicationResultAttemptClient
): GameService {
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun newAttemptForUser(userId: Long, attemptId: Long, correct: Boolean): GameStats {
        // 처음엔 답이 맞았을 때만 점수를 줌
        if (correct) {
            val scoreCard = ScoreCard(userId, attemptId)
            scoreCardRepository.save(scoreCard)

            logger.info("사용자 ID: $userId, 점수: $scoreCard.score 점, 답안 ID: $attemptId")

            val badgeCards = processForBadges(userId, attemptId)

            return GameStats(
                userId,
                scoreCard.score,
                badgeCards.map { it.badge })
        }

        return GameStats.emptyStats(userId)
    }

    override fun retrieveStatsForUser(userId: Long): GameStats {
        val score = scoreCardRepository.getTotalScoreForUser(userId)
        val badgeCards = badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId)

        return GameStats(
            userId,
            score,
            badgeCards.map { it.badge })
    }

    /**
     * 조건이 충족될 경우 새 배지를 제공하기 위해 얻은 총 점수와 점수 카드를 확인
     */
    fun processForBadges(userId: Long, attemptId: Long): List<BadgeCard> {
        val currentGotBadgeCards = mutableListOf<BadgeCard>()

        val totalScore = scoreCardRepository.getTotalScoreForUser(userId)
        logger.info("사용자 ID $userId 의 새로운 점수 $totalScore")

        val scoreCards = scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId)
        val badgeCards = badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId)

        // 점수 기반 배지
        checkAndGiveBadgeBasedOnScore(badgeCards, Badge.BRONZE_MULTIPLICATOR, totalScore, 100, userId)
            ?.let { currentGotBadgeCards.add(it) }
        checkAndGiveBadgeBasedOnScore(badgeCards, Badge.BRONZE_MULTIPLICATOR, totalScore, 100, userId)
            ?.let { currentGotBadgeCards.add(it) }
        checkAndGiveBadgeBasedOnScore(badgeCards, microservices.book.gamification.domain.Badge.BRONZE_MULTIPLICATOR, totalScore, 100, userId)
            ?.let { currentGotBadgeCards.add(it) }

        // 첫번째 정답 배지
        if (scoreCards.size == 1 && !containsBadge(badgeCards, Badge.FIRST_WON)) {
            giveBadgeToUser(Badge.FIRST_WON, userId)
                .let { currentGotBadgeCards.add(it) }
        }

        // 행운의 숫자 배지
        val attempt = attemptClient.retrieveMultiplicationResultAttemptById(attemptId)

        if (!containsBadge(badgeCards, Badge.LUCKY_NUMBER) && LUCKY_NUMBER == attempt.multiplicationFactorA ||
                LUCKY_NUMBER == attempt.multiplicationFactorB) {
            giveBadgeToUser(Badge.LUCKY_NUMBER, userId)
                .let { currentGotBadgeCards.add(it) }
        }

        return currentGotBadgeCards
    }

    /**
     * 배지를 얻기 위한 조건을 넘는지 체크하는 편의성 메소드
     * 또한 조건이 충족되면 사용자에게 배지를 부여
     */
    private fun checkAndGiveBadgeBasedOnScore(badgeCards: List<BadgeCard>, badge: Badge, score: Int, scoreThreshold: Int, userId: Long): BadgeCard? {
        if (score >= scoreThreshold && !containsBadge(badgeCards, badge)) {
            return giveBadgeToUser(badge, userId)
        }

        return null
    }

    /**
     * 배지 목록에 해당 배지가 포함돼 있는지 확인하는 메소드
     */
    private fun containsBadge(badgeCards: List<BadgeCard>, badge: Badge): Boolean {
        return badgeCards.map { badgeCard ->  badgeCard.badge}
            .contains(badge)
    }

    /**
     * 주어진 사용자에게 새로운 배지를 부여하는 메소드
     */
    private fun giveBadgeToUser(badge: Badge, userId: Long): BadgeCard {
        val badgeCard: BadgeCard =
            BadgeCard(userId, badge)
        val savedBadgeCard = badgeCardRepository.save(badgeCard)

        logger.info("사용자 ID $userId, 새로운 배지 획득: $badge")

        return savedBadgeCard
    }

    companion object {
        private const val LUCKY_NUMBER = 42
    }
}
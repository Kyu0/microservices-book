package microservices.book.gamification.service

import microservices.book.gamification.domain.LeaderBoardRow
import microservices.book.gamification.repository.ScoreCardRepository
import org.springframework.stereotype.Service

@Service
class LeaderBoardServiceImpl(
    private val scoreCardRepository: ScoreCardRepository
): LeaderBoardService {
    override fun getCurrentLeaderBoard(): List<LeaderBoardRow> {
        return scoreCardRepository.findFirst10()
    }
}
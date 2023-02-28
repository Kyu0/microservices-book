package microservies.book.gamification.controller

import microservies.book.gamification.domain.GameStats
import microservies.book.gamification.service.GameService
import org.springframework.web.bind.annotation.*

/**
 * Gamification 사용자 통계 서비스의 REST API
 */
@RestController
class UserStatsController(
    private val gameService: GameService
) {

    @GetMapping("/stats")
    fun getStatsForUser(@RequestParam("userId") userId: Long): GameStats {
        return gameService.retrieveStatsForUser(userId)
    }
}
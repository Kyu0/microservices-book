package microservies.book.gamification.controller

import microservies.book.gamification.domain.LeaderBoardRow
import microservies.book.gamification.service.LeaderBoardService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Gamification 리더보드 서비스의 REST API
 */
@RestController
class LeaderBoardController(
    @Autowired
    private val leaderBoardService: LeaderBoardService
) {

    @GetMapping("/leaders")
    fun getLeaderBoard(): List<LeaderBoardRow> {
        return leaderBoardService.getCurrentLeaderBoard()
    }
}
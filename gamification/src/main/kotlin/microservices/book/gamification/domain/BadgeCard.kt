package microservices.book.gamification.domain

import jakarta.persistence.*

@Entity
data class BadgeCard(
    @Column(name = "USER_ID")
    val userId: Long,
    val badge: microservices.book.gamification.domain.Badge
) {
    @Id @GeneratedValue
    @Column(name = "BADGE_ID")
    val badgeId: Long? = null

    @Column(name = "BADGE_TIMESTAMP")
    val badgeTimestamp: Long = System.currentTimeMillis()
}
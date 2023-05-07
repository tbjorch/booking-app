package tbjorch.booking.domain.user.repositories

import org.springframework.data.jpa.repository.JpaRepository
import tbjorch.booking.domain.common.valueobjects.UserId
import tbjorch.booking.domain.user.User
import tbjorch.booking.domain.user.valueobjects.EmailAddress

interface UserRepository : JpaRepository<User, UserId> {

    fun findByEmailAddress(emailAddress: EmailAddress): User?
}

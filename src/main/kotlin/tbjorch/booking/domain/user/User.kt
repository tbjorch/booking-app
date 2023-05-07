package tbjorch.booking.domain.user

import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import tbjorch.booking.domain.common.valueobjects.UserId
import tbjorch.booking.domain.user.valueobjects.EmailAddress

@Entity
class User private constructor(
    @EmbeddedId
    val id: UserId,
    val emailAddress: EmailAddress,
    val password: String,
) {
    companion object {
        fun create(emailAddress: EmailAddress, password: String): User {
            return User(UserId.create(), emailAddress, password)
        }
    }
}

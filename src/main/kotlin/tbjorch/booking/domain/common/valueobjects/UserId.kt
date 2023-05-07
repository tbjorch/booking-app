package tbjorch.booking.domain.common.valueobjects

import jakarta.persistence.Basic
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable
import java.util.UUID

@Embeddable
data class UserId private constructor(
    @field:Basic
    @Column(name = "user_id")
    val value: UUID,
) : Serializable {
    companion object {
        fun create() = UserId(UUID.randomUUID())
    }
}

package tbjorch.booking.domain.room.valueobjects

import jakarta.persistence.Basic
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable
import java.util.UUID

@Embeddable
data class RoomId private constructor(
    @field:Basic
    @Column(name = "room_id")
    val value: UUID,
) : Serializable {
    companion object {
        fun create(): RoomId {
            return RoomId(UUID.randomUUID())
        }

        fun from(roomId: UUID): RoomId {
            return RoomId(roomId)
        }
    }

    override fun toString(): String {
        return this.value.toString()
    }
}

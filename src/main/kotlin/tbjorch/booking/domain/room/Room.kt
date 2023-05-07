package tbjorch.booking.domain.room

import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import tbjorch.booking.domain.resource.Bookable
import tbjorch.booking.domain.resource.valueobjects.ResourceId
import tbjorch.booking.domain.room.valueobjects.RoomId
import tbjorch.booking.domain.room.valueobjects.RoomName
import java.time.Duration

@Entity
class Room private constructor(
    @EmbeddedId
    val id: RoomId,
    val name: RoomName,
) : Bookable {

    override val resourceId = ResourceId.from(id.toString())

    override val shortestBookingDuration = Duration.ofMinutes(30)

    override val bookingDurationStepSize = Duration.ofMinutes(15)

    companion object {
        fun create(roomName: RoomName): Room {
            return Room(RoomId.create(), roomName)
        }
    }
}

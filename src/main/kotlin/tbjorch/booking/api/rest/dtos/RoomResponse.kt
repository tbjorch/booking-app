package tbjorch.booking.api.rest.dtos

import tbjorch.booking.domain.room.Room
import java.util.UUID

data class RoomResponse(
    val roomId: UUID,
    val roomName: String,
) {
    companion object {
        fun from(room: Room): RoomResponse {
            return RoomResponse(roomId = room.id.value, roomName = room.name.toString())
        }
    }
}

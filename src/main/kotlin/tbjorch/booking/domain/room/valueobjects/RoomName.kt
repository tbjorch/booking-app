package tbjorch.booking.domain.room.valueobjects

import jakarta.persistence.Basic
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import tbjorch.booking.domain.room.exceptions.InvalidRoomNameException
import java.util.regex.Pattern

@Embeddable
data class RoomName(
    @field:Basic
    @Column(name = "room_name")
    val roomName: String
) {
    init {
        if (!isValid(roomName)) {
            throw InvalidRoomNameException("$roomName is not a valid name")
        }
    }

    companion object {
        private val nameRegexPattern = Pattern.compile("^[a-zA-Z]+\$")

        fun isValid(roomName: String): Boolean {
            return nameRegexPattern.matcher(roomName).matches()
        }
    }

    override fun toString(): String {
        return roomName
    }
}
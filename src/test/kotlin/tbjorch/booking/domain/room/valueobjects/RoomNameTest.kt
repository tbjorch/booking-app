package tbjorch.booking.domain.room.valueobjects

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import tbjorch.booking.domain.room.exceptions.InvalidRoomNameException

class RoomNameTest {
    @Test
    fun `Should allow room name with alphabetical characters`() {
        val lowerCase = "abcdefghijklmnopqrstuvwxyz"
        val upperCase = lowerCase.uppercase()
        assertDoesNotThrow {
            RoomName(lowerCase + upperCase)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["123", "not valid whitespaces", "#", "?"])
    fun `Should prevent invalid room names`(invalidName: String) {
        assertThrows<InvalidRoomNameException> {
            RoomName(invalidName)
        }
    }
}

package tbjorch.booking.application.services

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import tbjorch.booking.domain.resource.repositories.ResourceRepository
import tbjorch.booking.domain.room.repositories.RoomRepository
import java.time.Instant
import java.time.temporal.ChronoUnit.HOURS

@SpringBootTest
class RoomAppServiceIT {

    @Autowired
    private lateinit var roomAppService: RoomAppService

    @Autowired
    private lateinit var roomRepository: RoomRepository

    @Autowired
    private lateinit var resourceRepository: ResourceRepository

    @Test
    fun `Should create a Room and a corresponding Resource used for booking`() {
        // Arrange
        val roomName = "Test"

        // Act
        val roomId = roomAppService.createRoom(roomName).id

        // Assert
        val persistedRoom = roomRepository.findByIdOrNull(roomId)
        assertThat(persistedRoom).isNotNull
        val persistedResource = resourceRepository.findByIdOrNull(persistedRoom!!.resourceId)
        assertThat(persistedResource).isNotNull
    }

    @Test
    fun `Should support booking a Room`() {
        // Arrange
        val room = roomAppService.createRoom("Test")
        val startTime = Instant.now().truncatedTo(HOURS).plus(1, HOURS)
        val endTime = startTime.plus(1, HOURS)

        // Act
        roomAppService.bookRoom(room.id, startTime, endTime)

        // Assert
        val bookings = resourceRepository.findByIdOrNull(room.resourceId)!!.bookings
        assertThat(bookings.size).isEqualTo(1)
    }
}

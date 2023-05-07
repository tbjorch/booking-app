package tbjorch.booking.api.rest

import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import tbjorch.booking.application.services.RoomAppService
import tbjorch.booking.domain.room.Room
import tbjorch.booking.domain.room.valueobjects.RoomName

@WebMvcTest(RoomController::class)
class RoomControllerIT {

    @MockBean
    lateinit var roomAppService: RoomAppService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `Should create room`() {
        // Arrange
        Mockito.`when`(roomAppService.createRoom("testRoom")).thenReturn(Room.create(RoomName("testRoom")))

        // Assert
        mockMvc.perform(
            post("/v1/rooms").content("{\"roomName\": \"testRoom\"}").contentType(MediaType.APPLICATION_JSON),
        ).andExpect(status().is2xxSuccessful)
    }
}

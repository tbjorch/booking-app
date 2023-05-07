package tbjorch.booking.api.rest

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tbjorch.booking.api.rest.dtos.BookingResponse
import tbjorch.booking.api.rest.dtos.CreateBookingRequest
import tbjorch.booking.api.rest.dtos.CreateRoomRequest
import tbjorch.booking.api.rest.dtos.RoomResponse
import tbjorch.booking.application.services.RoomAppService
import tbjorch.booking.domain.room.valueobjects.RoomId
import java.util.UUID

@RestController
@RequestMapping("/v1/rooms")
class RoomController(
    private val roomAppService: RoomAppService,
) {

    @PostMapping
    fun createRoom(@RequestBody createRoomRequest: CreateRoomRequest): RoomResponse {
        val room = roomAppService.createRoom(createRoomRequest.roomName)
        return RoomResponse.from(room)
    }

    @GetMapping("{id}")
    fun getRoomById(@PathVariable("id") id: UUID): RoomResponse {
        val roomId = RoomId.from(id)
        val room = roomAppService.getRoom(roomId)
        return RoomResponse.from(room)
    }

    @PostMapping("{id}/bookings")
    fun bookRoom(@PathVariable("id") id: UUID, @RequestBody createBookingRequest: CreateBookingRequest): BookingResponse {
        val roomId = RoomId.from(id)
        val (startTime, endTime) = createBookingRequest
        val room = roomAppService.bookRoom(roomId, startTime, endTime)
        return BookingResponse.from(room)
    }
}

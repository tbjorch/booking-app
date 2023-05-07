package tbjorch.booking.application.services

import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import tbjorch.booking.application.exceptions.RoomNotFoundException
import tbjorch.booking.domain.resource.entities.Booking
import tbjorch.booking.domain.resource.valueobjects.BookingId
import tbjorch.booking.domain.room.Room
import tbjorch.booking.domain.room.repositories.RoomRepository
import tbjorch.booking.domain.room.valueobjects.RoomId
import tbjorch.booking.domain.room.valueobjects.RoomName
import java.time.Instant

@Service
class RoomAppService(
    private val roomRepository: RoomRepository,
    private val resourceAppService: ResourceAppService,
    private val bookingAppService: BookingAppService,
) {
    @Transactional
    fun createRoom(rawRoomName: String): Room {
        val roomName = RoomName(rawRoomName)
        val room = Room.create(roomName)
        resourceAppService.createResource(room)
        return roomRepository.save(room)
    }

    @Transactional
    fun bookRoom(roomId: RoomId, startTime: Instant, endTime: Instant): Booking {
        val room = getRoom(roomId)
        return bookingAppService.bookResource(room.resourceId, startTime, endTime)
    }

    @Transactional
    fun cancelRoomBooking(roomId: RoomId, bookingId: BookingId) {
        val room = getRoom(roomId)
        return bookingAppService.removeBooking(room.resourceId, bookingId)
    }

    fun getRoom(roomId: RoomId): Room {
        return roomRepository.findByIdOrNull(roomId)
            ?: throw RoomNotFoundException("No room found by id=$roomId")
    }
}

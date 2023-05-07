package tbjorch.booking.domain.room.repositories

import org.springframework.data.jpa.repository.JpaRepository
import tbjorch.booking.domain.room.Room
import tbjorch.booking.domain.room.valueobjects.RoomId

interface RoomRepository : JpaRepository<Room, RoomId>

package tbjorch.booking.domain.resource.entities

import jakarta.persistence.Embedded
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import tbjorch.booking.domain.common.valueobjects.UserId
import tbjorch.booking.domain.resource.valueobjects.BookingId
import tbjorch.booking.domain.resource.valueobjects.BookingPeriod
import java.time.Instant

@Entity
class Booking private constructor(
    @EmbeddedId
    val bookingId: BookingId,
    val userId: UserId,
    @Embedded
    val bookingPeriod: BookingPeriod,
) {
    companion object {
        fun create(userId: UserId, startTime: Instant, endTime: Instant): Booking {
            val bookingPeriod = BookingPeriod.create(startTime, endTime)
            return Booking(BookingId.create(), userId, bookingPeriod)
        }
    }

    fun duration() = bookingPeriod.duration()

    fun isOverlappingWith(other: Booking): Boolean =
        this.bookingPeriod.isOverlappingWith(other.bookingPeriod)
}

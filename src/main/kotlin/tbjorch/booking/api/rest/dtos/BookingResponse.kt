package tbjorch.booking.api.rest.dtos

import tbjorch.booking.domain.resource.entities.Booking
import java.time.Instant

data class BookingResponse private constructor(
    val bookingId: String,
    val bookingStartTime: Instant,
    val bookingEndTime: Instant,
) {
    companion object {
        fun from(booking: Booking) =
            BookingResponse(
                bookingId = booking.bookingId.toString(),
                bookingStartTime = booking.bookingPeriod.startTime,
                bookingEndTime = booking.bookingPeriod.endTime,
            )
    }
}

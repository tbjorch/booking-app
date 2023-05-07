package tbjorch.booking.api.rest.dtos

import java.time.Instant

data class CreateBookingRequest(
    val startTime: Instant,
    val endTime: Instant,
)

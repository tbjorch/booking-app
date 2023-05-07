package tbjorch.booking.utils

import tbjorch.booking.domain.common.valueobjects.UserId
import tbjorch.booking.domain.resource.Bookable
import tbjorch.booking.domain.resource.Resource
import tbjorch.booking.domain.resource.entities.Booking
import tbjorch.booking.domain.resource.valueobjects.ResourceId
import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit

object Fixtures {

    fun booking(
        userId: UserId = UserId.create(),
        startTime: Instant = Instant.now().truncatedTo(ChronoUnit.HOURS).plusSeconds(7200),
        endTime: Instant = Instant.now().truncatedTo(ChronoUnit.HOURS).plusSeconds(10800),
    ) =
        Booking.create(userId, startTime, endTime)

    fun resource(
        bookable: Bookable = bookable(),
    ) = Resource.create(bookable)

    fun bookable(
        minBookingDuration: Long = 1,
        stepSize: Long = 1,
    ) = object : Bookable {
        override val resourceId = ResourceId.create()
        override val shortestBookingDuration = Duration.ofMinutes(minBookingDuration)
        override val bookingDurationStepSize = Duration.ofMinutes(stepSize)
    }
}

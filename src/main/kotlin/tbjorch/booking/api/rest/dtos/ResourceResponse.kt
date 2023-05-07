package tbjorch.booking.api.rest.dtos

import tbjorch.booking.domain.resource.Resource
import tbjorch.booking.domain.resource.valueobjects.BookingPeriod
import java.util.UUID

data class ResourceResponse private constructor(
    val resourceId: UUID,
    val bookings: Set<BookingPeriod>,
) {
    companion object {
        fun from(resource: Resource): ResourceResponse {
            val bookingPeriods = resource.bookings.map { it.bookingPeriod }.toSet()
            return ResourceResponse(
                resourceId = resource.id.value,
                bookings = bookingPeriods,
            )
        }
    }
}

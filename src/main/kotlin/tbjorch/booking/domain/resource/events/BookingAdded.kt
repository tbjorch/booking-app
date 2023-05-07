package tbjorch.booking.domain.resource.events

import tbjorch.booking.domain.resource.entities.Booking
import tbjorch.booking.domain.resource.valueobjects.ResourceId

data class BookingAdded(
    val resourceId: ResourceId,
    val booking: Booking
)

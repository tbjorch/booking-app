package tbjorch.booking.domain.resource.services

import tbjorch.booking.domain.common.valueobjects.UserId
import tbjorch.booking.domain.resource.Resource
import tbjorch.booking.domain.resource.entities.Booking
import java.time.Instant

class BookingDomainService {
    fun bookResource(userId: UserId, resource: Resource, startTime: Instant, endTime: Instant): Booking {
        val newBooking = Booking.create(userId, startTime, endTime)
        resource.addBooking(newBooking)
        return newBooking
    }
}

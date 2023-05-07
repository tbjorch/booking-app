package tbjorch.booking.application.services

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import tbjorch.booking.domain.common.valueobjects.UserId
import tbjorch.booking.domain.resource.entities.Booking
import tbjorch.booking.domain.resource.repositories.ResourceRepository
import tbjorch.booking.domain.resource.services.BookingDomainService
import tbjorch.booking.domain.resource.valueobjects.BookingId
import tbjorch.booking.domain.resource.valueobjects.ResourceId
import java.time.Instant

@Service
class BookingAppService(
    private val resourceAppService: ResourceAppService,
    private val resourceRepository: ResourceRepository
) {

    private val bookingDomainService = BookingDomainService()

    @Transactional
    fun bookResource(resourceId: ResourceId, startTime: Instant, endTime: Instant): Booking {
        val resource = resourceAppService.getResource(resourceId)
        val booking = bookingDomainService.bookResource(UserId.create(), resource, startTime, endTime)
        resourceRepository.save(resource)
        return booking
    }

    @Transactional
    fun removeBooking(resourceId: ResourceId, bookingId: BookingId) {
        val resource = resourceAppService.getResource(resourceId)
        resource.removeBooking(bookingId)
    }
}

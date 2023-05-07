package tbjorch.booking.api.rest

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tbjorch.booking.api.rest.dtos.BookingResponse
import tbjorch.booking.api.rest.dtos.CreateBookingRequest
import tbjorch.booking.application.services.BookingAppService
import tbjorch.booking.domain.resource.valueobjects.BookingId
import tbjorch.booking.domain.resource.valueobjects.ResourceId

@RestController
@RequestMapping("/v1/resources")
class BookingController(
    private val bookingAppService: BookingAppService,
) {
    @PostMapping("{resourceId}/book")
    fun bookResource(
        @PathVariable("resourceId") resourceId: String,
        @RequestBody createBookingRequest: CreateBookingRequest,
    ): BookingResponse {
        val id = ResourceId.from(resourceId)
        val (startTime, endTime) = createBookingRequest
        val booking = bookingAppService.bookResource(id, startTime, endTime)
        return BookingResponse.from(booking)
    }

    @DeleteMapping("{resourceId}/bookings/{bookingId}")
    fun removeBooking(
        @PathVariable("resourceId") resourceId: String,
        @PathVariable("bookingId") bookingId: String,
    ) {
        val resourceId = ResourceId.from(resourceId)
        val bookingId = BookingId.from(bookingId)
        bookingAppService.removeBooking(resourceId, bookingId)
    }
}

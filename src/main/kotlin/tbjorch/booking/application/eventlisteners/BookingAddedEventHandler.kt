package tbjorch.booking.application.eventlisteners

import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import tbjorch.booking.domain.resource.events.BookingAdded

@Component
class BookingAddedEventHandler {

    private val logger = LoggerFactory.getLogger(javaClass)

    @EventListener
    fun handleBookingAddedEvent(bookingAdded: BookingAdded) {
        logger.info("Booking added for resourceId=${bookingAdded.resourceId}")
    }
}

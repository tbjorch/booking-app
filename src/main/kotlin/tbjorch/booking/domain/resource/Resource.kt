package tbjorch.booking.domain.resource

import jakarta.persistence.CascadeType
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.OneToMany
import org.springframework.data.domain.AbstractAggregateRoot
import tbjorch.booking.domain.resource.entities.Booking
import tbjorch.booking.domain.resource.events.BookingAdded
import tbjorch.booking.domain.resource.events.ResourceCreated
import tbjorch.booking.domain.resource.exceptions.BookingNotFoundException
import tbjorch.booking.domain.resource.exceptions.InvalidBookingDurationException
import tbjorch.booking.domain.resource.exceptions.ResourceNotAvailableForBookingException
import tbjorch.booking.domain.resource.valueobjects.BookingId
import tbjorch.booking.domain.resource.valueobjects.ResourceId
import java.time.Duration
import java.time.Instant

@Entity
class Resource private constructor(
    @EmbeddedId val id: ResourceId,
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    private val _bookings: MutableSet<Booking> = mutableSetOf(),
    val minimumBookingDuration: Duration,
    val bookingDurationStepSize: Duration,
) : AbstractAggregateRoot<Resource>() {
    val bookings: Set<Booking> get() = _bookings

    fun isAvailableFor(booking: Booking): Boolean =
        !bookings.any { it.isOverlappingWith(booking) }

    fun addBooking(booking: Booking) {
        verifyBookingDurationAboveMinimum(booking)
        verifyBookingDurationIsMultipleOfSteps(booking)
        verifyAvailableForBooking(booking)
        this._bookings.add(booking)
        registerEvent(BookingAdded(id, booking))
    }

    private fun verifyBookingDurationAboveMinimum(booking: Booking) {
        val durationAboveMinimum = booking.duration() >= this.minimumBookingDuration
        if (!durationAboveMinimum) {
            throw InvalidBookingDurationException("Booking duration is below minimum allowed duration for resource")
        }
    }

    private fun verifyBookingDurationIsMultipleOfSteps(booking: Booking) {
        val quotientWithoutRemainder = booking.duration().dividedBy(bookingDurationStepSize)
        val comparisonValue = bookingDurationStepSize.multipliedBy(quotientWithoutRemainder)
        val bookingIsMultipleOfStepSize = comparisonValue == booking.duration()
        if (!bookingIsMultipleOfStepSize) {
            throw InvalidBookingDurationException("Booking duration is not a multiple of bookingDurationStepSize")
        }
    }

    private fun verifyAvailableForBooking(booking: Booking) {
        if (!isAvailableFor(booking)) {
            throw ResourceNotAvailableForBookingException("Resource not available at ${booking.bookingPeriod}")
        }
    }

    fun getBooking(bookingId: BookingId): Booking {
        return this._bookings.find { it.bookingId == bookingId }
            ?: throw BookingNotFoundException("No booking found by id=$bookingId")
    }

    fun removeBooking(bookingId: BookingId) {
        val booking = getBooking(bookingId)
        this._bookings.remove(booking)
    }

    fun hasUnfulfilledBookings(): Boolean {
        return this._bookings.find { it.bookingPeriod.endTime > Instant.now() } != null
    }

    companion object {
        fun create(bookable: Bookable): Resource {
            val resource = Resource(
                id = bookable.resourceId,
                minimumBookingDuration = bookable.shortestBookingDuration,
                bookingDurationStepSize = bookable.bookingDurationStepSize,
            )
            resource.registerEvent(ResourceCreated(resource.id))
            return resource
        }
    }

    override fun equals(other: Any?): Boolean {
        return other is Resource && other.id == this.id
    }
}

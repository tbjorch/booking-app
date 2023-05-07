package tbjorch.booking.domain.resource

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import tbjorch.booking.domain.resource.exceptions.BookingNotFoundException
import tbjorch.booking.domain.resource.exceptions.InvalidBookingDurationException
import tbjorch.booking.domain.resource.exceptions.ResourceNotAvailableForBookingException
import tbjorch.booking.utils.Fixtures

class ResourceTest {

    @Test
    fun `Should be available for booking`() {
        val booking = Fixtures.booking()
        val resource = Fixtures.resource()
        assertThat(resource.isAvailableFor(booking)).isTrue
    }

    @Test
    fun `Should not be available for booking`() {
        val booking = Fixtures.booking()
        val resource = Fixtures.resource()
        resource.addBooking(booking)
        assertThat(resource.isAvailableFor(booking)).isFalse
    }

    @Test
    fun `Should allow booking available resource`() {
        val booking = Fixtures.booking()
        val resource = Fixtures.resource()
        assertDoesNotThrow {
            resource.addBooking(booking)
        }
    }

    @Test
    fun `Should prevent overlapping bookings`() {
        val booking = Fixtures.booking()
        val resource = Fixtures.resource()
        resource.addBooking(booking)
        assertThrows<ResourceNotAvailableForBookingException> {
            resource.addBooking(booking)
        }
    }

    @Test
    fun `Should find booking`() {
        val booking = Fixtures.booking()
        val resource = Fixtures.resource()
        resource.addBooking(booking)
        assertThat(resource.getBooking(booking.bookingId)).isEqualTo(booking)
    }

    @Test
    fun `Should throw not found on non-existing booking`() {
        val booking = Fixtures.booking()
        val resource = Fixtures.resource()
        assertThrows<BookingNotFoundException> {
            resource.getBooking(booking.bookingId)
        }
    }

    @Test
    fun `Should remove booking`() {
        val booking = Fixtures.booking()
        val resource = Fixtures.resource()
        resource.addBooking(booking)
        resource.removeBooking(booking.bookingId)
        assertThat(resource.bookings).isEmpty()
    }

    @Test
    fun `Should throw not found when trying to remove non-existing booking`() {
        val booking = Fixtures.booking()
        val resource = Fixtures.resource()
        assertThrows<BookingNotFoundException> {
            resource.removeBooking(booking.bookingId)
        }
    }

    @Test
    fun `Should throw InvalidDurationException if duration is below minimum for resource`() {
        val bookable = Fixtures.bookable(minBookingDuration = 120)
        val resource = Fixtures.resource(bookable)
        val invalidDurationBooking = Fixtures.booking()
        assertThrows<InvalidBookingDurationException> {
            resource.addBooking(invalidDurationBooking)
        }.also {
            assertThat(it).hasMessageContaining("Booking duration is below minimum allowed duration for resource")
        }
    }

    @Test
    fun `Should throw InvalidDurationException if duration is not a multiple of the duration step size`() {
        val bookable = Fixtures.bookable(stepSize = 13)
        val resource = Fixtures.resource(bookable)
        val invalidDurationBooking = Fixtures.booking()
        assertThrows<InvalidBookingDurationException> {
            resource.addBooking(invalidDurationBooking)
        }.also {
            assertThat(it).hasMessageContaining("Booking duration is not a multiple of bookingDurationStepSize")
        }
    }
}

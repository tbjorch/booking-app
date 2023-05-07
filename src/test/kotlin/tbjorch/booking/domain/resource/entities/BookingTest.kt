package tbjorch.booking.domain.resource.entities

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import tbjorch.booking.utils.Fixtures
import java.time.Instant
import java.time.temporal.ChronoUnit

class BookingTest {

    private val hourInSeconds = 3600L

    @Test
    fun `Should return true on overlapping bookings`() {
        val booking = Fixtures.booking()
        val overlappingBooking = Fixtures.booking(startTime = booking.bookingPeriod.startTime.plusSeconds(1800))
        assertThat(booking.isOverlappingWith(overlappingBooking)).isTrue
    }

    @Test
    fun `Should return false on non-overlapping bookings`() {
        val booking = Fixtures.booking()
        val startTime = Instant.now().truncatedTo(ChronoUnit.HOURS).plusSeconds(24 * hourInSeconds)
        val overlappingBooking = Fixtures.booking(startTime = startTime, endTime = startTime.plusSeconds(hourInSeconds))
        assertThat(booking.isOverlappingWith(overlappingBooking)).isFalse
    }
}

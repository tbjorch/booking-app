package tbjorch.booking.domain.resource.services

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import tbjorch.booking.domain.common.valueobjects.UserId
import tbjorch.booking.domain.resource.valueobjects.BookingPeriod
import tbjorch.booking.utils.Fixtures
import java.time.Instant
import java.time.temporal.ChronoUnit

class BookingDomainServiceTest {

    private val bookingDomainService = BookingDomainService()

    @Test
    fun `Should create booking and add to resource`() {
        val userId = UserId.create()
        val resource = Fixtures.resource()
        val startTime = Instant.now().truncatedTo(ChronoUnit.HOURS).plusSeconds(7200)
        val endTime = startTime.plusSeconds(3600)

        val booking = bookingDomainService.bookResource(userId, resource, startTime, endTime)

        assertThat(booking.bookingId).isNotNull
        assertThat(booking.userId).isEqualTo(userId)
        assertThat(booking.bookingPeriod).isEqualTo(BookingPeriod.create(startTime, endTime))
    }
}

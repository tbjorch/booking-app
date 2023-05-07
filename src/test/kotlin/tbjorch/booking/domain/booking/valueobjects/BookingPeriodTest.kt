package tbjorch.booking.domain.booking.valueobjects

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import tbjorch.booking.domain.resource.valueobjects.BookingPeriod
import java.time.Instant
import java.time.temporal.ChronoUnit

class BookingPeriodTest {

    private val hourInSeconds = 3600L
    private val minuteInSeconds = 60L

    @Test
    fun `Should allow start time before end time`() {
        val startTime = Instant.now().truncatedTo(ChronoUnit.HOURS).plusSeconds(hourInSeconds)
        val endTime = startTime.plusSeconds(hourInSeconds)

        assertDoesNotThrow {
            BookingPeriod.create(startTime, endTime)
        }
    }

    @Test
    fun `BookingPeriod starttime must be in the future`() {
        val startTime = Instant.now()
        val endTime = startTime.minusNanos(1)

        assertThrows<IllegalArgumentException> {
            BookingPeriod.create(startTime, endTime)
        }
    }

    @Test
    fun `Should not allow start time after end time`() {
        val startTime = Instant.now()
        val endTime = startTime.minusNanos(1)

        assertThrows<IllegalArgumentException> {
            BookingPeriod.create(startTime, endTime)
        }
    }

    @Test
    fun `Should not allow start time same as end time`() {
        val startTime = Instant.now()

        assertThrows<IllegalArgumentException> {
            BookingPeriod.create(startTime, startTime)
        }
    }

    @ParameterizedTest
    @ValueSource(longs = [0, 15, 30, 45])
    fun `Should allow quarter precision on minutes`(minutes: Long) {
        val quartersToAdd = minutes * minuteInSeconds
        val startTime =
            Instant.now()
                .truncatedTo(ChronoUnit.HOURS)
                .plusSeconds(2 * hourInSeconds)
                .plusSeconds(quartersToAdd)
        val endTime = startTime.plusSeconds(hourInSeconds).plusSeconds(quartersToAdd)

        assertDoesNotThrow {
            BookingPeriod.create(startTime, endTime)
        }
    }

    @ParameterizedTest
    @ValueSource(longs = [1, 13, 23, 47])
    fun `Should not allow other minute values than whole quarters`(minutes: Long) {
        val quartersToAdd = minutes * minuteInSeconds
        val startTime =
            Instant.now()
                .truncatedTo(ChronoUnit.HOURS)
                .plusSeconds(2 * hourInSeconds)
                .plusSeconds(quartersToAdd)
        val endTime = startTime.plusSeconds(hourInSeconds).plusSeconds(quartersToAdd)

        assertThrows<IllegalArgumentException> {
            BookingPeriod.create(startTime, endTime)
        }
    }

    @Test
    fun `Should not allow precision higher than minutes`() {
        val startTime =
            Instant.now()
                .truncatedTo(ChronoUnit.HOURS)
                .plusSeconds(2 * hourInSeconds)
                .plusMillis(1)
        val endTime = startTime.plusSeconds(hourInSeconds)

        assertThrows<IllegalArgumentException> {
            BookingPeriod.create(startTime, endTime)
        }
    }

    @Test
    fun `Should not be overlapping periods`() {
        val startTime = truncatedNowPlusTwoHours()
        val bookingPeriod = BookingPeriod.create(startTime, startTime.plusSeconds(hourInSeconds))
        val nonOverlappingBookingPeriod =
            BookingPeriod.create(startTime.plusSeconds(hourInSeconds), startTime.plusSeconds(2 * hourInSeconds))
        assertThat(bookingPeriod.isOverlappingWith(nonOverlappingBookingPeriod)).isFalse
    }

    @Test
    fun `Should be overlapping periods`() {
        val startTime = truncatedNowPlusTwoHours()
        val endTime = startTime.plusSeconds(hourInSeconds)
        val bookingPeriod = BookingPeriod.create(startTime, endTime)
        val overlappingPeriod =
            BookingPeriod.create(endTime.minusSeconds(15 * minuteInSeconds), endTime.plusSeconds(hourInSeconds))
        assertThat(bookingPeriod.isOverlappingWith(overlappingPeriod)).isTrue
    }

    private fun truncatedNowPlusTwoHours() = Instant.now()
        .truncatedTo(ChronoUnit.HOURS)
        .plusSeconds(2 * hourInSeconds)
}

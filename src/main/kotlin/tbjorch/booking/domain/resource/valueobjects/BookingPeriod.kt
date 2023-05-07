package tbjorch.booking.domain.resource.valueobjects

import jakarta.persistence.Basic
import jakarta.persistence.Embeddable
import java.time.Duration
import java.time.Instant
import java.time.ZoneId
import java.time.temporal.ChronoUnit

@Embeddable
data class BookingPeriod private constructor(
    @field:Basic
    val startTime: Instant,
    @field:Basic
    val endTime: Instant
) {
    companion object {
        fun create(startTime: Instant, endTime: Instant): BookingPeriod {
            return BookingPeriod(startTime, endTime)
        }
    }

    init {
        this.isValid()
    }

    fun duration() = Duration.between(startTime, endTime)

    fun isOverlappingWith(otherPeriod: BookingPeriod): Boolean =
        this.startTime < otherPeriod.endTime && this.endTime > otherPeriod.startTime

    override fun toString(): String {
        return "startTime: $startTime - endTime:$endTime"
    }

    private fun isValid() {
        if (startTime < Instant.now()) {
            throw IllegalArgumentException("Start time must be in the future.")
        }
        if (startTime.isAfter(endTime)) {
            throw IllegalArgumentException("Start time must be before end time")
        }
        if (startTime == endTime) {
            throw IllegalArgumentException("End time must be after start time")
        }
        val startTimeAsZoneDateTime = startTime.atZone(ZoneId.systemDefault())
        val endTimeAsZoneDateTime = endTime.atZone(ZoneId.systemDefault())
        if (startTimeAsZoneDateTime.minute % 15 != 0 || endTimeAsZoneDateTime.minute % 15 != 0) {
            throw IllegalArgumentException("Minute values must be quarters of an hour (0, 15, 30, 45)")
        }
        if (startTime.truncatedTo(ChronoUnit.MINUTES) != startTime || endTime.truncatedTo(ChronoUnit.MINUTES) != endTime) {
            throw IllegalArgumentException("Provided time precision must be in minutes")
        }
    }
}

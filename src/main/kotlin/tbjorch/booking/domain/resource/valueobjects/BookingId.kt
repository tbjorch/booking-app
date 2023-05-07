package tbjorch.booking.domain.resource.valueobjects

import jakarta.persistence.Basic
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable
import java.util.UUID

@Embeddable
data class BookingId private constructor(
    @field:Basic
    @Column(name = "booking_id")
    val value: UUID,
) : Serializable {
    companion object {
        fun create(): BookingId {
            return BookingId(UUID.randomUUID())
        }

        fun from(bookingId: String): BookingId {
            return BookingId(UUID.fromString(bookingId))
        }
    }

    override fun toString(): String {
        return this.value.toString()
    }
}

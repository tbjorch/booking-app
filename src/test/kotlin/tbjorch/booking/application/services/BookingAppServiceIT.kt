package tbjorch.booking.application.services

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import tbjorch.booking.domain.resource.exceptions.ResourceNotAvailableForBookingException
import tbjorch.booking.domain.resource.repositories.ResourceRepository
import tbjorch.booking.utils.Fixtures

@SpringBootTest
class BookingAppServiceIT {

    @Autowired
    private lateinit var bookingAppService: BookingAppService

    @Autowired
    private lateinit var resourceRepository: ResourceRepository

    @Test
    fun `Should support booking a resource`() {
        // Arrange
        val resourceId = resourceRepository.save(Fixtures.resource()).id
        val expectedBooking = Fixtures.booking()
        val expectedStartTime = expectedBooking.bookingPeriod.startTime
        val expectedEndTime = expectedBooking.bookingPeriod.endTime

        // Act
        bookingAppService.bookResource(resourceId, expectedStartTime, expectedEndTime)

        // Assert
        val persistedResource = resourceRepository.findByIdOrNull(resourceId)!!
        assertThat(persistedResource.bookings).hasSize(1)
        with(persistedResource.bookings.first()) {
            assertThat(bookingPeriod.startTime).isEqualTo(expectedStartTime)
            assertThat(bookingPeriod.endTime).isEqualTo(expectedEndTime)
        }
    }

    @Test
    fun `Should support cancelling a booking`() {
        // Arrange
        val bookingToCancel = Fixtures.booking()
        val newResource = Fixtures.resource()
        newResource.addBooking(bookingToCancel)
        val persistedResorce = resourceRepository.save(newResource)

        // Act
        bookingAppService.removeBooking(persistedResorce.id, persistedResorce.bookings.first().bookingId)

        // Assert
        val updatedResource = resourceRepository.findByIdOrNull(persistedResorce.id)!!
        assertThat(updatedResource.bookings).hasSize(0)
    }
}

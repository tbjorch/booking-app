package tbjorch.booking.api.rest.dtos

data class CreateResourceRequest(
    val minBookingDurationInMinutes: Long,
    val bookingTimeStepInMinutes: Long,
)

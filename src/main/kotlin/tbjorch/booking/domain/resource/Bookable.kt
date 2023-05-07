package tbjorch.booking.domain.resource

import tbjorch.booking.domain.resource.valueobjects.ResourceId
import java.time.Duration

interface Bookable {

    val resourceId: ResourceId

    /**
     * The shortest possible duration of a booking.
     */
    val shortestBookingDuration: Duration

    /**
     * The step size used when booking.
     *
     * E.g. step size 15 minutes means that the time between start and end of a booking
     * is a multiple of 15 minutes.
     */
    val bookingDurationStepSize: Duration
}

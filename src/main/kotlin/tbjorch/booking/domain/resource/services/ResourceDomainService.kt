package tbjorch.booking.domain.resource.services

import tbjorch.booking.domain.resource.Resource
import tbjorch.booking.domain.resource.exceptions.ResourceException

class ResourceDomainService {
    fun verifyResourceIsDeletable(resource: Resource) {
        if (resource.hasUnfulfilledBookings()) {
            throw ResourceException("Resource has unfulfilled bookings. Not possible to delete")
        }
    }
}

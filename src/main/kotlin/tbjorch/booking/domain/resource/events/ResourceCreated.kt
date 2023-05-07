package tbjorch.booking.domain.resource.events

import tbjorch.booking.domain.resource.valueobjects.ResourceId

data class ResourceCreated(val resourceId: ResourceId)
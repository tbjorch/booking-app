package tbjorch.booking.domain.resource.repositories

import org.springframework.data.jpa.repository.JpaRepository
import tbjorch.booking.domain.resource.Resource
import tbjorch.booking.domain.resource.valueobjects.ResourceId

interface ResourceRepository : JpaRepository<Resource, ResourceId>

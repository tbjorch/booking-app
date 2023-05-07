package tbjorch.booking.application.services

import jakarta.transaction.Transactional
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import tbjorch.booking.application.exceptions.ResourceNotFoundException
import tbjorch.booking.domain.resource.Bookable
import tbjorch.booking.domain.resource.Resource
import tbjorch.booking.domain.resource.events.ResourceDeleted
import tbjorch.booking.domain.resource.repositories.ResourceRepository
import tbjorch.booking.domain.resource.services.ResourceDomainService
import tbjorch.booking.domain.resource.valueobjects.ResourceId

@Service
class ResourceAppService(
    private val resourceRepository: ResourceRepository,
    private val eventPublisher: ApplicationEventPublisher,
) {

    private val resourceDomainService = ResourceDomainService()

    fun createResource(bookable: Bookable): Resource {
        val resource = Resource.create(bookable)
        return resourceRepository.save(resource)
    }

    @Transactional
    fun deleteResource(resourceId: ResourceId) {
        val resource = getResource(resourceId)
        resourceDomainService.verifyResourceIsDeletable(resource)
        resourceRepository.delete(resource)
        eventPublisher.publishEvent(ResourceDeleted(resourceId))
    }

    fun getResource(resourceId: ResourceId): Resource {
        return resourceRepository.findByIdOrNull(resourceId)
            ?: throw ResourceNotFoundException("No resource found by id=$resourceId")
    }
}

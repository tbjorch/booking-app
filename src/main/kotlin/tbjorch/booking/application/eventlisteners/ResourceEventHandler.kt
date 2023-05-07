package tbjorch.booking.application.eventlisteners

import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import tbjorch.booking.domain.resource.events.ResourceCreated
import tbjorch.booking.domain.resource.events.ResourceDeleted

@Component
class ResourceEventHandler {

    private val logger = LoggerFactory.getLogger(javaClass)

    @EventListener
    fun handleResourceCreatedEvent(resourceCreated: ResourceCreated) {
        logger.info("Resource created with id=${resourceCreated.resourceId}")
    }

    @EventListener
    fun handleResourceDeletedEvent(resourceDeleted: ResourceDeleted) {
        logger.info("Resource deleted with id=${resourceDeleted.resourceId}")
    }
}
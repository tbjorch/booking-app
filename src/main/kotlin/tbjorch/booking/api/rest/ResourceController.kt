package tbjorch.booking.api.rest

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tbjorch.booking.api.rest.dtos.CreateResourceRequest
import tbjorch.booking.api.rest.dtos.ResourceResponse
import tbjorch.booking.domain.resource.Bookable
import tbjorch.booking.application.services.ResourceAppService
import tbjorch.booking.domain.resource.valueobjects.ResourceId
import java.time.Duration

@RestController
@RequestMapping("/v1/resources")
class ResourceController(
    private val resourceAppService: ResourceAppService,
) {

    @PostMapping
    fun createResource(@RequestBody createResourceRequest: CreateResourceRequest): ResourceResponse {
        val bookable = bookableFrom(createResourceRequest)
        val resource = resourceAppService.createResource(bookable)
        return ResourceResponse.from(resource)
    }

    @GetMapping(path = ["{id}"])
    fun getResource(@PathVariable("id") rawResourceId: String): ResourceResponse {
        val resourceId = ResourceId.from(rawResourceId)
        val resource = resourceAppService.getResource(resourceId)
        return ResourceResponse.from(resource)
    }

    @DeleteMapping(path = ["{id}"])
    fun deleteResource(@PathVariable("id") rawResourceId: String) {
        val resourceId = ResourceId.from(rawResourceId)
        resourceAppService.deleteResource(resourceId)
    }

    private fun bookableFrom(createResourceRequest: CreateResourceRequest) = object : Bookable {
        override val resourceId = ResourceId.create()
        override val shortestBookingDuration = Duration.ofMinutes(createResourceRequest.minBookingDurationInMinutes)
        override val bookingDurationStepSize = Duration.ofMinutes(createResourceRequest.bookingTimeStepInMinutes)
    }
}

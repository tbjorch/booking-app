package tbjorch.booking.application.services

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import tbjorch.booking.application.exceptions.ResourceNotFoundException
import tbjorch.booking.domain.resource.Resource
import tbjorch.booking.domain.resource.repositories.ResourceRepository
import tbjorch.booking.domain.resource.valueobjects.ResourceId
import tbjorch.booking.utils.Fixtures

@SpringBootTest
class ResourceAppServiceIT {

    @Autowired
    private lateinit var resourceRepository: ResourceRepository

    @Autowired
    private lateinit var resourceAppService: ResourceAppService

    @Test
    fun `Should support creating new resources`() {
        // Act
        val createdResource = resourceAppService.createResource(Fixtures.bookable())

        // Assert
        val persistedResource = resourceRepository.findByIdOrNull(createdResource.id)
        assertThat(persistedResource).isNotNull
    }

    @Test
    fun `Should delete resource`() {
        // Arrange
        val resourceId = resourceRepository.save(Resource.create(Fixtures.bookable())).id

        // Act
        resourceAppService.deleteResource(resourceId)

        // Assert
        assertThat(resourceRepository.findByIdOrNull(resourceId)).isNull()
    }

    @Test
    fun `Should throw exception if trying to delete non-existing resource`() {
        assertThrows<ResourceNotFoundException> {
            resourceAppService.deleteResource(ResourceId.create())
        }
    }

    @Test
    fun `Should return resource object`() {
        // Arrange
        val resource = Resource.create(Fixtures.bookable())
        resourceRepository.save(resource)

        // Act
        val retrievedResource = resourceAppService.getResource(resource.id)

        // Assert
        assertThat(retrievedResource).isEqualTo(resource)
    }

    @Test
    fun `Should throw exception if trying to retrieve non-existing resource`() {
        assertThrows<ResourceNotFoundException> {
            resourceAppService.getResource(ResourceId.create())
        }
    }
}

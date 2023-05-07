package tbjorch.booking.domain.resource.valueobjects

import jakarta.persistence.Basic
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable
import java.util.UUID

@Embeddable
class ResourceId private constructor(
    @field:Basic
    @Column(name = "resource_id")
    val value: UUID,
) : Serializable {
    companion object {
        fun create(): ResourceId {
            return ResourceId(UUID.randomUUID())
        }

        fun from(resourceId: String): ResourceId {
            return ResourceId(UUID.fromString(resourceId))
        }
    }

    override fun toString(): String {
        return this.value.toString()
    }
}

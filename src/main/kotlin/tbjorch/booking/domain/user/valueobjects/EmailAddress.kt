package tbjorch.booking.domain.user.valueobjects

import jakarta.persistence.Basic
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.lang.IllegalArgumentException
import java.util.regex.Pattern

@Embeddable
data class EmailAddress private constructor(
    @field:Basic
    @Column(name = "emailAddress")
    val value: String,
) {

    fun isValid(): Boolean {
        return isValid(value)
    }

    companion object {
        private val owaspEmailRegexPattern =
            Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}\$")

        fun isValid(emailAddress: String) =
            owaspEmailRegexPattern.matcher(emailAddress).matches()

        fun from(emailAddress: String): EmailAddress {
            if (!isValid(emailAddress)) {
                throw IllegalArgumentException("Invalid email address")
            }
            return EmailAddress(emailAddress)
        }
    }
}

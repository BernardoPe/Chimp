package pt.isel.pdm.chimp.domain.messages

import pt.isel.pdm.chimp.domain.Failure
import pt.isel.pdm.chimp.domain.Identifiable
import pt.isel.pdm.chimp.domain.Success
import pt.isel.pdm.chimp.domain.user.User
import pt.isel.pdm.chimp.domain.wrappers.identifier.Identifier
import pt.isel.pdm.chimp.domain.wrappers.identifier.toIdentifier
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

/**
 * Represents a message in a channel.
 *
 * @property id The unique identifier of the message.
 * @property channelId The unique identifier of the channel where the message was sent.
 * @property user The user that sent the message.
 * @property content The content of the message.
 * @property createdAt The date and time when the message was sent.
 * @property editedAt The date and time when the message was last edited.
 */
data class Message(
    override val id: Identifier = Identifier(0),
    val channelId: Identifier,
    val user: User,
    val content: String,
    val createdAt: LocalDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS),
    val editedAt: LocalDateTime? = null,
) : Identifiable {
    companion object {
        private val validator = MessageValidator()

        operator fun invoke(
            id: Long = 0,
            channelId: Long,
            user: User,
            content: String,
            createdAt: LocalDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS),
            editedAt: LocalDateTime? = null,
        ): Message =
            Message(
                id.toIdentifier(),
                channelId.toIdentifier(),
                user,
                content,
                createdAt,
                editedAt,
            )
    }

    init {
        val validation = validator.validate(content)
        require(validation is Success) { (validation as Failure).value.toErrorMessage() }
    }

    /**
     * Edits the content of the message.
     *
     * @param content the new content of the message
     * @return a new message with the updated content
     */
    fun edit(content: String): Message = copy(content = content, editedAt = LocalDateTime.now())
}

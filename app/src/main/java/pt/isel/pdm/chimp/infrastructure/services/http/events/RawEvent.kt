package pt.isel.pdm.chimp.infrastructure.services.http.events

import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.readUTF8Line

/**
 * Represents a raw SSE received from the server.
 *
 * @property id The event's identifier.
 * @property type The event's type.
 * @property data The event's data.
 */
internal data class RawEvent(
    val id: String,
    val type: String,
    val data: String,
)

internal suspend fun ByteReadChannel.readRawEvent(name: String): RawEvent {
    val id = readUTF8Line()?.removePrefix("id:") ?: throw IllegalStateException("Invalid event: missing type")
    val data = readUTF8Line()?.removePrefix("data:") ?: throw IllegalStateException("Invalid event: missing data")
    return RawEvent(id, name.removePrefix("event:"), data)
}

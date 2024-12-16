package pt.isel.pdm.chimp.infrastructure.storage.firestore.dto

import pt.isel.pdm.chimp.domain.channel.Channel
import pt.isel.pdm.chimp.domain.channel.ChannelRole
import pt.isel.pdm.chimp.domain.wrappers.identifier.toIdentifier
import pt.isel.pdm.chimp.domain.wrappers.name.toName

data class ChannelPOJO(
    var id: Long = 0,
    var name: String = "",
    var defaultRole: String = "",
    var owner: UserPOJO = UserPOJO(),
    var isPublic: Boolean = false,
    var members: List<ChannelMemberPOJO> = emptyList(),
) {
    fun toDomain() =
        Channel(
            id = id.toIdentifier(),
            name = name.toName(),
            defaultRole = ChannelRole.valueOf(defaultRole),
            owner = owner.toDomain(),
            isPublic = isPublic,
            members = members.map { it.toDomain() },
        )
}

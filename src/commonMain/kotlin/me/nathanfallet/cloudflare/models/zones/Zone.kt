package me.nathanfallet.cloudflare.models.zones

import kotlinx.serialization.Serializable
import me.nathanfallet.usecases.models.IModel

@Serializable
data class Zone(
    override val id: String,
    val name: String?,
) : IModel<String, CreateZonePayload, Unit>

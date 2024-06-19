package me.nathanfallet.cloudflare.models.zones

import dev.kaccelero.models.IModel
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.nathanfallet.cloudflare.models.accounts.Account

@Serializable
data class Zone(
    override val id: String,
    val name: String? = null,
    val account: Account? = null,
    val createdOn: Instant? = null,
    val activatedOn: Instant? = null,
    val modifiedOn: Instant? = null,
    @SerialName("original_dnshost") val originalDNSHost: String? = null,
    val originalNameServers: List<String>? = null,
    val originalRegistrar: String? = null,
    val vanityNameServers: List<String>? = null,
) : IModel<String, ZonePayload, ZonePayload>

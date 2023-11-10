package me.nathanfallet.cloudflare.models.zones

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.nathanfallet.cloudflare.models.accounts.Account
import me.nathanfallet.usecases.models.IModel

@Serializable
data class Zone(
    override val id: String,
    val name: String?,
    val account: Account?,
    val createdOn: Instant?,
    val activatedOn: Instant?,
    val modifiedOn: Instant?,
    @SerialName("original_dnshost") val originalDNSHost: String?,
    val originalNameServers: List<String>?,
    val originalRegistrar: String?,
    val vanityNameServers: List<String>?,
) : IModel<String, ZonePayload, ZonePayload>

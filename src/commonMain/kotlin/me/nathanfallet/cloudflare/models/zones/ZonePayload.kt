package me.nathanfallet.cloudflare.models.zones

import kotlinx.serialization.Serializable
import me.nathanfallet.cloudflare.models.accounts.Account

@Serializable
data class ZonePayload(
    val name: String,
    val account: Account,
    val type: String? = null
)

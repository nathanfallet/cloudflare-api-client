package me.nathanfallet.cloudflare.models

import kotlinx.serialization.Serializable

@Serializable
data class CloudflareMessage(
    val code: Int,
    val message: String
)

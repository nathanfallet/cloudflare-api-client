package me.nathanfallet.cloudflare.models

import kotlinx.serialization.Serializable

@Serializable
data class CloudflareResponse<T>(
    val messages: List<CloudflareMessage>,
    val errors: List<CloudflareMessage>,
    val success: Boolean,
    val result: T?
)

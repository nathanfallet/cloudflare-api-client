package me.nathanfallet.cloudflare.models.r2

import kotlinx.serialization.Serializable

@Serializable
data class Object(
    val key: String,
)

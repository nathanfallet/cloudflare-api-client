package me.nathanfallet.cloudflare.models.accounts

import kotlinx.serialization.Serializable

@Serializable
data class Account(
    val id: String,
    val name: String?
)

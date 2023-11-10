package me.nathanfallet.cloudflare.models.dns.records

import kotlinx.serialization.Serializable

@Serializable
data class DNSRecordPayload(
    val name: String,
    val content: String,
    val type: String,
    val proxied: Boolean? = null,
    val comment: String? = null,
    val tags: List<String>? = null,
    val ttl: Int? = null,
)

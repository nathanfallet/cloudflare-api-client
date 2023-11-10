package me.nathanfallet.cloudflare.models.dns.records

import kotlinx.serialization.Serializable

@Serializable
data class DNSRecordPayload(
    val name: String,
    val content: String,
    val type: String,
    val proxied: Boolean?,
    val comment: String?,
    val tags: List<String>?,
    val ttl: Int?,
)

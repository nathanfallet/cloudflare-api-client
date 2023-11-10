package me.nathanfallet.cloudflare.models.dns.records

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import me.nathanfallet.usecases.models.IChildModel

@Serializable
data class DNSRecord(
    override val id: String,
    val zoneId: String,
    val zoneName: String? = null,
    val name: String? = null,
    val type: String? = null,
    val content: String? = null,
    val proxied: Boolean? = null,
    val comment: String? = null,
    val createdOn: Instant? = null,
    val modifiedOn: Instant? = null,
    val locked: Boolean? = null,
    val proxiable: Boolean? = null,
    val tags: List<String>? = null,
    val ttl: Int? = null,
) : IChildModel<String, DNSRecordPayload, DNSRecordPayload, String> {

    override val parentId: String
        get() = zoneId

}

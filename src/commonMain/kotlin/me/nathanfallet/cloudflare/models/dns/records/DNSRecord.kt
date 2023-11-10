package me.nathanfallet.cloudflare.models.dns.records

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import me.nathanfallet.usecases.models.IChildModel

@Serializable
data class DNSRecord(
    override val id: String,
    val zoneId: String,
    val zoneName: String?,
    val name: String?,
    val type: String?,
    val content: String?,
    val proxied: Boolean?,
    val comment: String?,
    val createdOn: Instant?,
    val modifiedOn: Instant?,
    val locked: Boolean?,
    val proxiable: Boolean?,
    val tags: List<String>?,
    val ttl: Int?,
) : IChildModel<String, DNSRecordPayload, DNSRecordPayload, String> {

    override val parentId: String
        get() = zoneId

}

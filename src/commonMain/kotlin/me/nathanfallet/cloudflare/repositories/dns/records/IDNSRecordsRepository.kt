package me.nathanfallet.cloudflare.repositories.dns.records

import me.nathanfallet.cloudflare.models.dns.records.DNSRecord
import me.nathanfallet.cloudflare.models.dns.records.DNSRecordPayload

interface IDNSRecordsRepository {

    suspend fun list(zoneId: String): List<DNSRecord>
    suspend fun list(limit: Long, offset: Long, zoneId: String): List<DNSRecord>
    suspend fun create(payload: DNSRecordPayload, zoneId: String): DNSRecord?
    suspend fun delete(id: String, zoneId: String): Boolean
    suspend fun get(id: String, zoneId: String): DNSRecord?
    suspend fun update(id: String, payload: DNSRecordPayload, zoneId: String): DNSRecord?

}

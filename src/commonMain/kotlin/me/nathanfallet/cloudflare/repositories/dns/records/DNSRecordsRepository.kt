package me.nathanfallet.cloudflare.repositories.dns.records

import dev.kaccelero.models.IContext
import dev.kaccelero.models.RecursiveId
import dev.kaccelero.repositories.IChildModelRemoteRepository
import dev.kaccelero.repositories.Pagination
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import me.nathanfallet.cloudflare.client.ICloudflareClient
import me.nathanfallet.cloudflare.models.CloudflareResponse
import me.nathanfallet.cloudflare.models.dns.records.DNSRecord
import me.nathanfallet.cloudflare.models.dns.records.DNSRecordPayload
import me.nathanfallet.cloudflare.models.zones.Zone

class DNSRecordsRepository(
    private val cloudflareClient: ICloudflareClient,
) : IChildModelRemoteRepository<DNSRecord, String, DNSRecordPayload, DNSRecordPayload, String>, IDNSRecordsRepository {

    override suspend fun list(parentId: RecursiveId<*, String, *>, context: IContext?): List<DNSRecord> {
        return cloudflareClient.request(HttpMethod.Get, "/zones/${parentId.id}/dns_records")
            .body<CloudflareResponse<List<DNSRecord>>>().result ?: emptyList()
    }

    override suspend fun list(zoneId: String): List<DNSRecord> {
        return list(RecursiveId<Zone, String, Unit>(zoneId))
    }

    override suspend fun list(
        pagination: Pagination,
        parentId: RecursiveId<*, String, *>,
        context: IContext?,
    ): List<DNSRecord> {
        val page = (pagination.offset / pagination.limit) + 1
        return cloudflareClient.request(HttpMethod.Get, "/zones/${parentId.id}/dns_records") {
            parameter("per_page", pagination.limit)
            parameter("page", page)
        }.body<CloudflareResponse<List<DNSRecord>>>().result ?: emptyList()
    }

    override suspend fun list(pagination: Pagination, zoneId: String): List<DNSRecord> {
        return list(pagination, RecursiveId<Zone, String, Unit>(zoneId))
    }

    override suspend fun create(
        payload: DNSRecordPayload,
        parentId: RecursiveId<*, String, *>,
        context: IContext?,
    ): DNSRecord? {
        return cloudflareClient.request(HttpMethod.Post, "/zones/${parentId.id}/dns_records") {
            contentType(ContentType.Application.Json)
            setBody(payload)
        }.body<CloudflareResponse<DNSRecord>>().result
    }

    override suspend fun create(payload: DNSRecordPayload, zoneId: String): DNSRecord? {
        return create(payload, RecursiveId<Zone, String, Unit>(zoneId))
    }

    override suspend fun delete(id: String, parentId: RecursiveId<*, String, *>, context: IContext?): Boolean {
        return cloudflareClient.request(HttpMethod.Delete, "/zones/${parentId.id}/dns_records/$id")
            .body<CloudflareResponse<DNSRecord>>().result != null
    }

    override suspend fun delete(id: String, zoneId: String): Boolean {
        return delete(id, RecursiveId<Zone, String, Unit>(zoneId))
    }

    override suspend fun get(id: String, parentId: RecursiveId<*, String, *>, context: IContext?): DNSRecord? {
        return cloudflareClient.request(HttpMethod.Get, "/zones/${parentId.id}/dns_records/$id")
            .body<CloudflareResponse<DNSRecord>>().result
    }

    override suspend fun get(id: String, zoneId: String): DNSRecord? {
        return get(id, RecursiveId<Zone, String, Unit>(zoneId))
    }

    override suspend fun update(
        id: String,
        payload: DNSRecordPayload,
        parentId: RecursiveId<*, String, *>,
        context: IContext?,
    ): DNSRecord? {
        return cloudflareClient.request(HttpMethod.Put, "/zones/${parentId.id}/dns_records/$id") {
            contentType(ContentType.Application.Json)
            setBody(payload)
        }.body<CloudflareResponse<DNSRecord>>().result
    }

    override suspend fun update(id: String, payload: DNSRecordPayload, zoneId: String): DNSRecord? {
        return update(id, payload, RecursiveId<Zone, String, Unit>(zoneId))
    }

}

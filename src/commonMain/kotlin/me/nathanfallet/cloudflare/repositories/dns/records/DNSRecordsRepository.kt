package me.nathanfallet.cloudflare.repositories.dns.records

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import me.nathanfallet.cloudflare.client.CloudflareClient
import me.nathanfallet.cloudflare.models.CloudflareResponse
import me.nathanfallet.cloudflare.models.dns.records.DNSRecord
import me.nathanfallet.cloudflare.models.dns.records.DNSRecordPayload

class DNSRecordsRepository(
    private val cloudflareClient: CloudflareClient
) : IDNSRecordsRepository {

    override suspend fun list(parentId: String): List<DNSRecord> {
        return cloudflareClient.createRequest(HttpMethod.Get, "/zones/$parentId/dns_records")
            .body<CloudflareResponse<List<DNSRecord>>>().result ?: emptyList()
    }

    override suspend fun list(limit: Long, offset: Long, parentId: String): List<DNSRecord> {
        val page = (offset / limit) + 1
        return cloudflareClient.createRequest(HttpMethod.Get, "/zones/$parentId/dns_records") {
            parameter("per_page", limit)
            parameter("page", page)
        }.body<CloudflareResponse<List<DNSRecord>>>().result ?: emptyList()
    }

    override suspend fun create(payload: DNSRecordPayload, parentId: String): DNSRecord? {
        return cloudflareClient.createRequest(HttpMethod.Post, "/zones/$parentId/dns_records") {
            contentType(ContentType.Application.Json)
            setBody(payload)
        }.body<CloudflareResponse<DNSRecord>>().result
    }

    override suspend fun delete(id: String, parentId: String): Boolean {
        return cloudflareClient.createRequest(HttpMethod.Delete, "/zones/$parentId/dns_records/$id")
            .body<CloudflareResponse<DNSRecord>>().result != null
    }

    override suspend fun get(id: String, parentId: String): DNSRecord? {
        return cloudflareClient.createRequest(HttpMethod.Get, "/zones/$parentId/dns_records/$id")
            .body<CloudflareResponse<DNSRecord>>().result
    }

    override suspend fun update(id: String, payload: DNSRecordPayload, parentId: String): Boolean {
        return cloudflareClient.createRequest(HttpMethod.Put, "/zones/$parentId/dns_records/$id") {
            contentType(ContentType.Application.Json)
            setBody(payload)
        }.body<CloudflareResponse<DNSRecord>>().result != null
    }

}

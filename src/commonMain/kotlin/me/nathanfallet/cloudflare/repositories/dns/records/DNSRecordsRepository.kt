package me.nathanfallet.cloudflare.repositories.dns.records

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import me.nathanfallet.cloudflare.client.CloudflareClient
import me.nathanfallet.cloudflare.models.CloudflareResponse
import me.nathanfallet.cloudflare.models.dns.records.DNSRecord
import me.nathanfallet.cloudflare.models.dns.records.DNSRecordPayload
import me.nathanfallet.usecases.context.IContext
import me.nathanfallet.usecases.models.id.RecursiveId

class DNSRecordsRepository(
    private val cloudflareClient: CloudflareClient,
) : IDNSRecordsRepository {

    override suspend fun list(parentId: RecursiveId<*, String, *>, context: IContext?): List<DNSRecord> {
        return cloudflareClient.request(HttpMethod.Get, "/zones/${parentId.id}/dns_records")
            .body<CloudflareResponse<List<DNSRecord>>>().result ?: emptyList()
    }

    override suspend fun list(
        limit: Long,
        offset: Long,
        parentId: RecursiveId<*, String, *>,
        context: IContext?,
    ): List<DNSRecord> {
        val page = (offset / limit) + 1
        return cloudflareClient.request(HttpMethod.Get, "/zones/${parentId.id}/dns_records") {
            parameter("per_page", limit)
            parameter("page", page)
        }.body<CloudflareResponse<List<DNSRecord>>>().result ?: emptyList()
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

    override suspend fun delete(id: String, parentId: RecursiveId<*, String, *>, context: IContext?): Boolean {
        return cloudflareClient.request(HttpMethod.Delete, "/zones/${parentId.id}/dns_records/$id")
            .body<CloudflareResponse<DNSRecord>>().result != null
    }

    override suspend fun get(id: String, parentId: RecursiveId<*, String, *>, context: IContext?): DNSRecord? {
        return cloudflareClient.request(HttpMethod.Get, "/zones/${parentId.id}/dns_records/$id")
            .body<CloudflareResponse<DNSRecord>>().result
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

}

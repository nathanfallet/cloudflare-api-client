package me.nathanfallet.cloudflare.repositories.zones

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import me.nathanfallet.cloudflare.client.CloudflareClient
import me.nathanfallet.cloudflare.models.CloudflareResponse
import me.nathanfallet.cloudflare.models.zones.Zone
import me.nathanfallet.cloudflare.models.zones.ZonePayload
import me.nathanfallet.usecases.context.IContext

class ZonesRepository(
    private val cloudflareClient: CloudflareClient,
) : IZonesRepository {

    override suspend fun list(context: IContext?): List<Zone> {
        return cloudflareClient.createRequest(HttpMethod.Get, "/zones")
            .body<CloudflareResponse<List<Zone>>>().result ?: emptyList()
    }

    override suspend fun list(limit: Long, offset: Long, context: IContext?): List<Zone> {
        val page = (offset / limit) + 1
        return cloudflareClient.createRequest(HttpMethod.Get, "/zones") {
            parameter("per_page", limit)
            parameter("page", page)
        }.body<CloudflareResponse<List<Zone>>>().result ?: emptyList()
    }

    override suspend fun create(payload: ZonePayload, context: IContext?): Zone? {
        return cloudflareClient.createRequest(HttpMethod.Post, "/zones") {
            contentType(ContentType.Application.Json)
            setBody(payload)
        }.body<CloudflareResponse<Zone>>().result
    }

    override suspend fun delete(id: String, context: IContext?): Boolean {
        return cloudflareClient.createRequest(HttpMethod.Delete, "/zones/$id")
            .body<CloudflareResponse<Zone>>().result != null
    }

    override suspend fun get(id: String, context: IContext?): Zone? {
        return cloudflareClient.createRequest(HttpMethod.Get, "/zones/$id")
            .body<CloudflareResponse<Zone>>().result
    }

    override suspend fun update(id: String, payload: ZonePayload, context: IContext?): Boolean {
        return cloudflareClient.createRequest(HttpMethod.Put, "/zones/$id") {
            contentType(ContentType.Application.Json)
            setBody(payload)
        }.body<CloudflareResponse<Zone>>().result != null
    }

    override suspend fun purgeCache(id: String, values: List<String>, key: String) {
        cloudflareClient.createRequest(HttpMethod.Post, "/zones/$id/purge_cache") {
            contentType(ContentType.Application.Json)
            setBody(mapOf(key to values))
        }
    }

}

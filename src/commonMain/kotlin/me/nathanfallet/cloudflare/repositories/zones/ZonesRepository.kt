package me.nathanfallet.cloudflare.repositories.zones

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import me.nathanfallet.cloudflare.client.CloudflareClient
import me.nathanfallet.cloudflare.models.CloudflareResponse
import me.nathanfallet.cloudflare.models.zones.Zone
import me.nathanfallet.cloudflare.models.zones.ZonePayload
import me.nathanfallet.usecases.context.IContext
import me.nathanfallet.usecases.models.repositories.remote.IModelRemoteRepository

class ZonesRepository(
    private val cloudflareClient: CloudflareClient,
) : IZonesRepository, IModelRemoteRepository<Zone, String, ZonePayload, ZonePayload> {

    override suspend fun list(context: IContext?): List<Zone> {
        return cloudflareClient.request(HttpMethod.Get, "/zones")
            .body<CloudflareResponse<List<Zone>>>().result ?: emptyList()
    }

    override suspend fun list(): List<Zone> {
        return list(null)
    }

    override suspend fun list(limit: Long, offset: Long, context: IContext?): List<Zone> {
        val page = (offset / limit) + 1
        return cloudflareClient.request(HttpMethod.Get, "/zones") {
            parameter("per_page", limit)
            parameter("page", page)
        }.body<CloudflareResponse<List<Zone>>>().result ?: emptyList()
    }

    override suspend fun list(limit: Long, offset: Long): List<Zone> {
        return list(limit, offset, null)
    }

    override suspend fun create(payload: ZonePayload, context: IContext?): Zone? {
        return cloudflareClient.request(HttpMethod.Post, "/zones") {
            contentType(ContentType.Application.Json)
            setBody(payload)
        }.body<CloudflareResponse<Zone>>().result
    }

    override suspend fun create(payload: ZonePayload): Zone? {
        return create(payload, null)
    }

    override suspend fun delete(id: String, context: IContext?): Boolean {
        return cloudflareClient.request(HttpMethod.Delete, "/zones/$id")
            .body<CloudflareResponse<Zone>>().result != null
    }

    override suspend fun delete(id: String): Boolean {
        return delete(id, null)
    }

    override suspend fun get(id: String, context: IContext?): Zone? {
        return cloudflareClient.request(HttpMethod.Get, "/zones/$id")
            .body<CloudflareResponse<Zone>>().result
    }

    override suspend fun get(id: String): Zone? {
        return get(id, null)
    }

    override suspend fun update(id: String, payload: ZonePayload, context: IContext?): Zone? {
        return cloudflareClient.request(HttpMethod.Put, "/zones/$id") {
            contentType(ContentType.Application.Json)
            setBody(payload)
        }.body<CloudflareResponse<Zone>>().result
    }

    override suspend fun update(id: String, payload: ZonePayload): Zone? {
        return update(id, payload, null)
    }

    override suspend fun purgeCache(id: String, values: List<String>, key: String) {
        cloudflareClient.request(HttpMethod.Post, "/zones/$id/purge_cache") {
            contentType(ContentType.Application.Json)
            setBody(mapOf(key to values))
        }
    }

}

package me.nathanfallet.cloudflare.repositories.zones

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import me.nathanfallet.cloudflare.client.CloudflareClient
import me.nathanfallet.cloudflare.models.CloudflareResponse
import me.nathanfallet.cloudflare.models.zones.CreateZonePayload
import me.nathanfallet.cloudflare.models.zones.Zone

class ZonesRepository(
    private val cloudflareClient: CloudflareClient
) : IZonesRepository {

    override suspend fun create(payload: CreateZonePayload): Zone? {
        return cloudflareClient.createRequest(HttpMethod.Post, "/zones") {
            contentType(ContentType.Application.Json)
            setBody(payload)
        }.body<CloudflareResponse<Zone>>().result
    }

    override suspend fun delete(id: String): Boolean {
        return cloudflareClient.createRequest(HttpMethod.Delete, "/zones/$id")
            .body<CloudflareResponse<Zone>>().result != null
    }

    override suspend fun get(id: String): Zone? {
        return cloudflareClient.createRequest(HttpMethod.Get, "/zones/$id")
            .body<CloudflareResponse<Zone>>().result
    }

    override suspend fun update(id: String, payload: Unit): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun list(): List<Zone> {
        return cloudflareClient.createRequest(HttpMethod.Get, "/zones")
            .body<CloudflareResponse<List<Zone>>>().result ?: emptyList()
    }

}

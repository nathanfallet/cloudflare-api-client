package me.nathanfallet.cloudflare.client

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import me.nathanfallet.cloudflare.models.CloudflareResponse
import me.nathanfallet.cloudflare.models.zones.CreateZonePayload
import me.nathanfallet.cloudflare.models.zones.Zone

class CloudflareClient(
    private val token: String
) : ICloudflareClient {

    companion object {

        const val BASE_URL = "https://api.cloudflare.com/client/v4"

    }

    @OptIn(ExperimentalSerializationApi::class)
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                explicitNulls = false
            })
        }
    }

    private suspend fun createRequest(
        method: HttpMethod,
        url: String,
        builder: HttpRequestBuilder.() -> Unit = {}
    ): HttpResponse {
        return httpClient.request(BASE_URL + url) {
            this.method = method
            header("Authorization", "Bearer $token")
            builder()
        }
    }

    override suspend fun listZones(): CloudflareResponse<List<Zone>> {
        return createRequest(HttpMethod.Get, "/zones").body()
    }

    override suspend fun getZone(id: String): CloudflareResponse<Zone> {
        return createRequest(HttpMethod.Get, "/zones/$id").body()
    }

    override suspend fun createZone(payload: CreateZonePayload): CloudflareResponse<Zone> {
        return createRequest(HttpMethod.Post, "/zones") {
            contentType(ContentType.Application.Json)
            setBody(payload)
        }.body()
    }

    override suspend fun deleteZone(id: String): CloudflareResponse<Zone> {
        return createRequest(HttpMethod.Delete, "/zones/$id").body()
    }

}

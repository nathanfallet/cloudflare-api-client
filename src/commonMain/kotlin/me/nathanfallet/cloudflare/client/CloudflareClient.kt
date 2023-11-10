package me.nathanfallet.cloudflare.client

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import me.nathanfallet.cloudflare.repositories.zones.ZonesRepository

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

    internal suspend fun createRequest(
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

    override val zones = ZonesRepository(this)

}

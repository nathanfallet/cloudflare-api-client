package me.nathanfallet.cloudflare.client

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
import me.nathanfallet.cloudflare.repositories.dns.records.DNSRecordsRepository
import me.nathanfallet.cloudflare.repositories.zones.ZonesRepository
import me.nathanfallet.ktorx.models.api.AbstractAPIClient

@OptIn(ExperimentalSerializationApi::class)
class CloudflareClient(
    private val token: String,
) : AbstractAPIClient(
    "https://api.cloudflare.com/client/v4",
    json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
        namingStrategy = JsonNamingStrategy.SnakeCase
    }
), ICloudflareClient {

    override suspend fun request(
        method: HttpMethod,
        path: String,
        builder: HttpRequestBuilder.() -> Unit,
    ): HttpResponse {
        return super.request(method, path) {
            header("Authorization", "Bearer $token")
            builder()
        }
    }

    override val zones = ZonesRepository(this)
    override val dnsRecords = DNSRecordsRepository(this)

}

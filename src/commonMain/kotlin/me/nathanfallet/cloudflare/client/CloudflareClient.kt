package me.nathanfallet.cloudflare.client

import dev.kaccelero.client.AbstractAPIClient
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import me.nathanfallet.cloudflare.models.application.CloudflareJson
import me.nathanfallet.cloudflare.repositories.dns.records.DNSRecordsRepository
import me.nathanfallet.cloudflare.repositories.zones.ZonesRepository

class CloudflareClient(
    private val token: String,
) : AbstractAPIClient(
    "https://api.cloudflare.com/client/v4",
    json = CloudflareJson.json
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

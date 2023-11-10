package me.nathanfallet.cloudflare.client

import me.nathanfallet.cloudflare.models.CloudflareResponse
import me.nathanfallet.cloudflare.models.zones.CreateZonePayload
import me.nathanfallet.cloudflare.models.zones.Zone

interface ICloudflareClient {

    suspend fun listZones(): CloudflareResponse<List<Zone>>
    suspend fun getZone(id: String): CloudflareResponse<Zone>
    suspend fun createZone(payload: CreateZonePayload): CloudflareResponse<Zone>
    suspend fun deleteZone(id: String): CloudflareResponse<Zone>

}

package me.nathanfallet.cloudflare.repositories.zones

import me.nathanfallet.cloudflare.models.zones.Zone
import me.nathanfallet.cloudflare.models.zones.ZonePayload

interface IZonesRepository {

    suspend fun list(): List<Zone>
    suspend fun list(limit: Long, offset: Long): List<Zone>
    suspend fun create(payload: ZonePayload): Zone?
    suspend fun delete(id: String): Boolean
    suspend fun get(id: String): Zone?
    suspend fun update(id: String, payload: ZonePayload): Zone?

    suspend fun purgeCache(id: String, values: List<String>, key: String = "files")

}


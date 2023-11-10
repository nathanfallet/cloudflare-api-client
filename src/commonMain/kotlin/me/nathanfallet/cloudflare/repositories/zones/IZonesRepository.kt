package me.nathanfallet.cloudflare.repositories.zones

import me.nathanfallet.cloudflare.models.zones.CreateZonePayload
import me.nathanfallet.cloudflare.models.zones.Zone
import me.nathanfallet.usecases.models.repositories.IModelSuspendRepository

interface IZonesRepository : IModelSuspendRepository<Zone, String, CreateZonePayload, Unit> {

    suspend fun list(): List<Zone>
    suspend fun purgeCache(id: String, values: List<String>, key: String = "files")

}


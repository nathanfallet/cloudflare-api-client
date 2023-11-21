package me.nathanfallet.cloudflare.repositories.zones

import me.nathanfallet.cloudflare.models.zones.Zone
import me.nathanfallet.cloudflare.models.zones.ZonePayload
import me.nathanfallet.usecases.models.repositories.IModelSuspendRepository

interface IZonesRepository : IModelSuspendRepository<Zone, String, ZonePayload, ZonePayload> {

    suspend fun purgeCache(id: String, values: List<String>, key: String = "files")

}


package me.nathanfallet.cloudflare.client

import me.nathanfallet.cloudflare.repositories.dns.records.IDNSRecordsRepository
import me.nathanfallet.cloudflare.repositories.zones.IZonesRepository
import me.nathanfallet.ktorx.models.api.IAPIClient

interface ICloudflareClient : IAPIClient {

    val zones: IZonesRepository
    val dnsRecords: IDNSRecordsRepository

}

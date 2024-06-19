package me.nathanfallet.cloudflare.client

import dev.kaccelero.client.IAPIClient
import me.nathanfallet.cloudflare.repositories.dns.records.IDNSRecordsRepository
import me.nathanfallet.cloudflare.repositories.zones.IZonesRepository

interface ICloudflareClient : IAPIClient {

    val zones: IZonesRepository
    val dnsRecords: IDNSRecordsRepository

}

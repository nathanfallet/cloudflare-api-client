package me.nathanfallet.cloudflare.client

import me.nathanfallet.cloudflare.repositories.dns.records.IDNSRecordsRepository
import me.nathanfallet.cloudflare.repositories.zones.IZonesRepository

interface ICloudflareClient {

    val zones: IZonesRepository
    val dnsRecords: IDNSRecordsRepository

}

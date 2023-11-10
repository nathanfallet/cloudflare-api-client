package me.nathanfallet.cloudflare.client

import me.nathanfallet.cloudflare.repositories.zones.IZonesRepository

interface ICloudflareClient {

    val zones: IZonesRepository

}

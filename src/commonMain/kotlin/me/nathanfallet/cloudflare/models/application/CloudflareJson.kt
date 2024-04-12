package me.nathanfallet.cloudflare.models.application

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy

object CloudflareJson {

    @OptIn(ExperimentalSerializationApi::class)
    val json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
        namingStrategy = JsonNamingStrategy.SnakeCase
    }

}

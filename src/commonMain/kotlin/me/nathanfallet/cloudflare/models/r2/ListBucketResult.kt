package me.nathanfallet.cloudflare.models.r2

import kotlinx.serialization.Serializable

@Serializable
data class ListBucketResult(
    val contents: List<Object>,
)

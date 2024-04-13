package me.nathanfallet.cloudflare.r2

import io.ktor.http.*
import me.nathanfallet.cloudflare.models.r2.InputStream
import me.nathanfallet.cloudflare.models.r2.ListBucketResult

interface IR2Client {

    suspend fun listObjectsV2(bucket: String, prefix: String = ""): ListBucketResult
    suspend fun putObject(bucket: String, path: String, stream: InputStream, contentType: ContentType)
    suspend fun deleteObject(bucket: String, path: String)

}

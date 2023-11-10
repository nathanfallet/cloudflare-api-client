package me.nathanfallet.cloudflare.r2

import io.ktor.http.*
import me.nathanfallet.cloudflare.models.r2.InputStream

interface IR2Client {

    suspend fun upload(path: String, stream: InputStream, contentType: ContentType)
    suspend fun delete(path: String)

}

package me.nathanfallet.cloudflare.r2

import dev.kaccelero.client.AbstractAPIClient
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.decodeFromString
import me.nathanfallet.cloudflare.models.application.CloudflareJson
import me.nathanfallet.cloudflare.models.r2.InputStream
import me.nathanfallet.cloudflare.models.r2.ListBucketResult
import nl.adaptivity.xmlutil.ExperimentalXmlUtilApi
import nl.adaptivity.xmlutil.serialization.XML
import uk.co.lucasweb.aws.v4.signer.HttpRequest
import uk.co.lucasweb.aws.v4.signer.Signer
import uk.co.lucasweb.aws.v4.signer.credentials.AwsCredentials
import java.net.URI
import java.time.format.DateTimeFormatter

class R2Client(
    private val id: String,
    private val secret: String,
    accountId: String,
) : AbstractAPIClient(
    "https://$accountId.r2.cloudflarestorage.com",
    json = CloudflareJson.json
), IR2Client {

    private val host = "$accountId.r2.cloudflarestorage.com"

    @OptIn(ExperimentalXmlUtilApi::class)
    private val xml = XML {
        policy = R2XMLPolicy
    }

    override suspend fun request(
        method: HttpMethod,
        path: String,
        builder: HttpRequestBuilder.() -> Unit,
    ): HttpResponse {
        val request = HttpRequest(method.value, URI(baseUrl + path))
        val contentSha256 = "UNSIGNED-PAYLOAD"
        val amzDate = Clock.System.now().toLocalDateTime(TimeZone.UTC).toJavaLocalDateTime().let {
            DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'").format(it)
        }
        val signature = Signer.builder()
            .awsCredentials(AwsCredentials(id, secret))
            .region("auto")
            .header("host", host)
            .header("x-amz-content-sha256", contentSha256)
            .header("x-amz-date", amzDate)
            .buildS3(request, contentSha256)
            .signature
        return super.request(method, path) {
            header("x-amz-content-sha256", contentSha256)
            header("x-amz-date", amzDate)
            header("Authorization", signature)
            builder()
        }
    }

    override suspend fun listObjectsV2(bucket: String, prefix: String): ListBucketResult =
        request(HttpMethod.Get, "/$bucket?list-type=2&prefix=$prefix").bodyAsText().let {
            xml.decodeFromString(it)
        }

    override suspend fun putObject(bucket: String, path: String, stream: InputStream, contentType: ContentType): Unit =
        withContext(Dispatchers.IO) {
            request(HttpMethod.Put, "/$bucket/${path.removePrefix("/")}") {
                contentType(contentType)
                setBody(stream.readBytes())
            }
        }

    override suspend fun deleteObject(bucket: String, path: String) {
        request(HttpMethod.Delete, "/$bucket/${path.removePrefix("/")}")
    }

}

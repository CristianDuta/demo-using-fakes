package crdu.demo.datasources

import org.http4k.core.ContentType.Companion.APPLICATION_JSON
import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Uri

class JsonPlaceholderDataSource(private val url: String, private val httpClient: HttpHandler) : DataSource {
    override fun fetch(request: Request) = httpClient(
        request
            .uri(Uri.of(url + request.uri.path))
            .header("Accept", APPLICATION_JSON.toHeaderValue())
    )
}

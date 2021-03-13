package crdu.demo.datasources

import org.http4k.core.Request
import org.http4k.core.Response

interface DataSource {
    fun fetch(request: Request): Response
}
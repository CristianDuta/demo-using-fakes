package crdu.demo.events

import org.http4k.core.Method
import org.http4k.core.Uri
import org.http4k.events.Event

data class IncomingHttpRequest(
    val uri: Uri,
    val method: Method,
    val latency: Long,
    val status: Int
) : Event
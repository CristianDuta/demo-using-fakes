package crdu.demo.events

import org.http4k.core.Method
import org.http4k.core.Uri
import org.http4k.events.Event
import org.http4k.filter.ResponseFilters

data class IncomingHttpRequest(
    val uri: Uri,
    val method: Method,
    val latency: Long,
    val status: Int
) : Event

fun logIncomingTraffic(eventLogger: EventLogger) = ResponseFilters.ReportHttpTransaction {
    eventLogger(IncomingHttpRequest(it.request.uri, it.request.method, it.duration.toMillis(), it.response.status.code))
}
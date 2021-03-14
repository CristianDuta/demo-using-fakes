package crdu.demo.events

import org.http4k.events.Event

data class GeneratePhotosActivityReport(
    val userId: Int
) : Event
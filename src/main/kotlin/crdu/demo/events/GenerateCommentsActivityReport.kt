package crdu.demo.events

import org.http4k.events.Event

data class GenerateCommentsActivityReport(
    val userId: Int
) : Event
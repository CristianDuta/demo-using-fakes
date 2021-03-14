package crdu.demo.events

import org.http4k.events.Event

data class GenerateTodosActivityReport(
    val userId: Int
) : Event
package crdu.demo.events

import org.http4k.events.EventFilter
import org.http4k.events.Events
import org.http4k.events.MetadataEvent
import org.http4k.events.plus

typealias EventLogger = Events

fun addEventType() = EventFilter { next ->
    { e ->
        val event = when (e) {
            is MetadataEvent -> e.event
            else -> e
        }
        next(e + ("type" to event.javaClass.simpleName))
    }
}
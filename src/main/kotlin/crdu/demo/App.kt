package crdu.demo

import crdu.demo.EnvironmentKeys.DEBUG
import crdu.demo.EnvironmentKeys.REPOSITORY_API_URL
import crdu.demo.datasources.DataSource
import crdu.demo.datasources.JsonPlaceholderDataSource
import crdu.demo.events.EventLogger
import crdu.demo.events.addEventType
import crdu.demo.events.logIncomingTraffic
import crdu.demo.events.logOutgoingTraffic
import crdu.demo.handlers.commentsActivityReportHandler
import crdu.demo.handlers.photosActivityReportHandler
import crdu.demo.handlers.todosActivityReportHandler
import crdu.demo.repositories.*
import org.http4k.cloudnative.env.Environment
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.then
import org.http4k.events.AutoMarshallingEvents
import org.http4k.events.EventFilters.AddTimestamp
import org.http4k.events.then
import org.http4k.filter.debug
import org.http4k.format.Jackson
import org.http4k.routing.bind
import org.http4k.routing.routes
import java.time.Clock

fun app(
    env: Environment,
    rawHttpClient: HttpHandler,
    baseEventLogger: EventLogger = AutoMarshallingEvents(Jackson),
    dataSourceAsDependency: DataSource? = null
): HttpHandler {
    val eventLogger = AddTimestamp(Clock.systemDefaultZone())
        .then(addEventType())
        .then(baseEventLogger)

    val httpClient = logOutgoingTraffic(eventLogger).then(
        if (DEBUG(env)) {
            rawHttpClient.debug(debugStream = true)
        } else {
            rawHttpClient
        }
    )

    val dataSource: DataSource =
        dataSourceAsDependency ?: JsonPlaceholderDataSource(REPOSITORY_API_URL(env), httpClient)

    return logIncomingTraffic(eventLogger).then(
        routes(
            "/activity" bind GET to routes(
                "/reports" bind GET to routes(
                    "/comments/{userId}" bind GET to commentsActivityReportHandler(
                        eventLogger,
                        PostRepository(dataSource),
                        CommentRepository(dataSource)
                    ),
                    "/photos/{userId}" bind GET to photosActivityReportHandler(
                        eventLogger,
                        AlbumRepository(dataSource),
                        PhotoRepository(dataSource)
                    ),
                    "/todos/{userId}" bind GET to todosActivityReportHandler(
                        eventLogger,
                        TodoRepository(dataSource)
                    )
                )
            )
        )
    )
}

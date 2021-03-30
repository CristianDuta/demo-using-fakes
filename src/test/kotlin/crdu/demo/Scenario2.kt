package crdu.demo

import crdu.demo.EnvironmentKeys.DEBUG
import crdu.demo.EnvironmentKeys.REPOSITORY_API_URL
import crdu.demo.fakes.FakeJsonPlaceholder
import crdu.demo.fixtures.CommentsActivityReportFixture
import crdu.demo.fixtures.PhotosActivityReportFixture
import crdu.demo.fixtures.TodosActivityReportFixture
import crdu.demo.handlers.CommentsActivityReport
import crdu.demo.handlers.PhotosActivityReport
import crdu.demo.handlers.TodosActivityReport
import org.http4k.client.ApacheClient
import org.http4k.cloudnative.env.Environment
import org.http4k.core.*
import org.http4k.filter.debug
import org.http4k.format.Jackson.auto
import org.http4k.server.SunHttp
import org.http4k.server.asServer
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * Scenario 2: Intercepting http calls
 */

class Scenario2 {
    private val env = Environment.EMPTY
        .with(
            DEBUG of false,
            REPOSITORY_API_URL of "http://jsonPlaceholder.crdu.fake"
        )

    private val interceptJsonPlaceholderRequests = Filter { next ->
        {
            when (it.uri.host) {
                Uri.of(REPOSITORY_API_URL(env)).host -> FakeJsonPlaceholder()(it)
                else -> next(it)
            }
        }
    }
    private val httpClient = interceptJsonPlaceholderRequests.then(ApacheClient())

    @Test
    fun `GIVEN FakeJsonPlaceholder WHEN request is made THEN return fake data from json files`() {
        // GIVEN
        val port = app(env, httpClient).asServer(SunHttp(0)).start().port()
        val appUrl = "http://localhost:$port"
        val apacheClient = ApacheClient()
        val userId = 1

        // WHEN
        val commentsActivityReportResponse = apacheClient(Request(Method.GET, "$appUrl/activity/reports/comments/$userId"))
        val commentsActivityReport = Body.auto<CommentsActivityReport>().toLens()(commentsActivityReportResponse)

        val photosActivityReportResponse = apacheClient(Request(Method.GET, "$appUrl/activity/reports/photos/$userId"))
        val photosActivityReport = Body.auto<PhotosActivityReport>().toLens()(photosActivityReportResponse)

        val todosActivityReportResponse = apacheClient(Request(Method.GET, "$appUrl/activity/reports/todos/$userId"))
        val todosActivityReport = Body.auto<TodosActivityReport>().toLens()(todosActivityReportResponse)

        // THEN
        assertEquals(CommentsActivityReportFixture.commentsActivityReport, commentsActivityReport)
        assertEquals(PhotosActivityReportFixture.photosActivityReport, photosActivityReport)
        assertEquals(TodosActivityReportFixture(userId).todosActivityReport, todosActivityReport)
    }
}

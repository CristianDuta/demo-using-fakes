package crdu.demo

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
import org.http4k.format.Jackson.auto
import org.http4k.server.SunHttp
import org.http4k.server.asServer
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * Scenario 2: Start service locally and pass in the URL
 */

class Scenario2 {
    private val port = FakeJsonPlaceholder().asServer(SunHttp(0)).start().port()
    private val env = Environment.EMPTY
        .with(
            EnvironmentKeys.DEBUG of false,
            EnvironmentKeys.REPOSITORY_API_URL of "http://localhost:$port"
        )

    @Test
    fun `GIVEN FakeJsonPlaceholder WHEN request is made THEN return fake data from json files`() {
        // GIVEN
        val port = app(env, ApacheClient()).asServer(SunHttp(0)).start().port()
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

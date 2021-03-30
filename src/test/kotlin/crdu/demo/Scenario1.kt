package crdu.demo

import crdu.demo.datasources.DataSource
import crdu.demo.fixtures.*
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
import org.mockito.Mockito
import kotlin.test.assertEquals

/**
 * Scenario 1: Provide mock or different implementation of the same interface
 */

class Scenario1 {
    private val env = Environment.EMPTY
        .with(
            EnvironmentKeys.DEBUG of false
        )

    @Test
    fun `GIVEN MockedDataSource WHEN request is made THEN return mocked data`() {
        // GIVEN
        val userId = 1
        val mockedDataSource = setupDataSourceMock(userId)

        val port = app(
            env = env,
            rawHttpClient = ApacheClient(),
            dataSourceAsDependency = mockedDataSource
        ).asServer(SunHttp(0)).start().port()

        val appUrl = "http://localhost:$port"
        val apacheClient = ApacheClient()

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

    @Suppress("SameParameterValue")
    private fun setupDataSourceMock(userId: Int): DataSource {
        val mockedDataSource = Mockito.mock(DataSource::class.java)

        val albums = AlbumsFixture(userId).albums
        Mockito.`when`(mockedDataSource.fetch(Request(Method.GET, "/users/$userId/albums"))).thenReturn(
            Response(Status.OK).with(Body.auto<Any>().toLens() of albums)
        )
        for (album in albums) {
            Mockito.`when`(mockedDataSource.fetch(Request(Method.GET, "/albums/${album.id}/photos")))
                .thenReturn(
                    Response(Status.OK).with(
                        Body.auto<Any>().toLens() of PhotosActivityReportFixture.photosActivityReport.photos.filter { photo ->
                            photo.albumId == album.id
                        }
                    )
                )
        }

        val posts = PostsFixture(userId).posts
        Mockito.`when`(mockedDataSource.fetch(Request(Method.GET, "/users/$userId/posts"))).thenReturn(
            Response(Status.OK).with(Body.auto<Any>().toLens() of posts)
        )
        for (post in posts) {
            Mockito.`when`(mockedDataSource.fetch(Request(Method.GET, "/posts/${post.id}/comments")))
                .thenReturn(
                    Response(Status.OK).with(
                        Body.auto<Any>().toLens() of CommentsActivityReportFixture.commentsActivityReport.comments.filter { comment ->
                            comment.postId == post.id
                        }
                    )
                )
        }

        val todos = TodosActivityReportFixture(userId).todosActivityReport.todos
        Mockito.`when`(mockedDataSource.fetch(Request(Method.GET, "/users/$userId/todos"))).thenReturn(
            Response(Status.OK).with(Body.auto<Any>().toLens() of todos)
        )

        return mockedDataSource
    }
}

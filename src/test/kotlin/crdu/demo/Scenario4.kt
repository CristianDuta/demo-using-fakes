package crdu.demo

import crdu.demo.datasources.DataSource
import crdu.demo.entities.Todo
import crdu.demo.handlers.TodosActivityReport
import org.http4k.client.ApacheClient
import org.http4k.cloudnative.env.Environment
import org.http4k.core.*
import org.http4k.format.Jackson.auto
import org.http4k.lens.LensFailure
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Scenario 4: Customizable Fake provided at test level (own implementation)
 */
@Suppress("Unused")
class Scenario4 {
    private val env = Environment.EMPTY
        .with(
            EnvironmentKeys.REPOSITORY_API_URL of "http://fake"
        )

    @Test
    fun `GIVEN request for todos activity report generation WHEN data source endpoint is not available THEN throw exception`() {
        val userId = 1
        val fakeDataSource: DataSource = object : DataSource {
            override fun fetch(request: Request): Response {
                return Response(Status.NOT_FOUND)
            }
        }
        val appHandler = app(env = env, rawHttpClient = ApacheClient(), dataSourceAsDependency = fakeDataSource)

        assertThrows<LensFailure> {
            appHandler(Request(Method.GET, "${EnvironmentKeys.REPOSITORY_API_URL(env)}/activity/reports/todos/$userId"))
        }
    }

    @Test
    fun `GIVEN request for todos activity report generation WHEN receiving weird data from api THEN throw exception`() {
        val dataWithUnknownStructure = object {
            val attributeOne = "valueOne"
            val attributeTwo = "valueTwo"
        }

        val userId = 1
        val fakeDataSource: DataSource = object : DataSource {
            override fun fetch(request: Request): Response {
                return Response(Status.OK).with(Body.auto<Any>().toLens() of listOf(dataWithUnknownStructure))
            }
        }
        val appHandler = app(env = env, rawHttpClient = ApacheClient(), dataSourceAsDependency = fakeDataSource)

        assertThrows<LensFailure> {
            appHandler(Request(Method.GET, "${EnvironmentKeys.REPOSITORY_API_URL(env)}/activity/reports/todos/$userId"))
        }
    }

    @Test
    fun `GIVEN request for todos activity report generation WHEN receiving valid data from api THEN throw exception`() {
        val todo = object {
            val userId = 1
            val id = 2
            val title = "quis ut nam facilis et officia qui"
            val completed = false
        }

        val userId = 1
        val fakeDataSource: DataSource = object : DataSource {
            override fun fetch(request: Request): Response {
                return Response(Status.OK).with(Body.auto<Any>().toLens() of listOf(todo))
            }
        }
        val appHandler = app(env = env, rawHttpClient = ApacheClient(), dataSourceAsDependency = fakeDataSource)

        val response = appHandler(Request(Method.GET, "${EnvironmentKeys.REPOSITORY_API_URL(env)}/activity/reports/todos/$userId"))
        val todosActivityReport: TodosActivityReport = Body.auto<TodosActivityReport>().toLens()(response)

        assertEquals(TodosActivityReport(
            total = 1,
            todos = listOf(Todo(
                todo.id,
                todo.userId,
                todo.title,
                todo.completed
            ))
        ), todosActivityReport)
    }
}

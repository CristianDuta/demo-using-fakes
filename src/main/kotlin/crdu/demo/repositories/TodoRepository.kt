package crdu.demo.repositories

import crdu.demo.datasources.DataSource
import crdu.demo.entities.Todo
import org.http4k.core.Body
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.format.Jackson.auto

class TodoRepository(private val dataSource: DataSource) {
    fun findAllForUser(userId: Int): List<Todo> {
        val response = dataSource.fetch(
            Request(Method.GET, "/users/$userId/todos")
        )

        return Body.auto<List<Todo>>().toLens()(response)
    }
}

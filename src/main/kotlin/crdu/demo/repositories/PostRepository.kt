package crdu.demo.repositories

import crdu.demo.datasources.DataSource
import crdu.demo.entities.Post
import org.http4k.core.Body
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.format.Jackson.auto

class PostRepository(private val dataSource: DataSource) {
    fun findAllForUser(userId: Int): List<Post> {
        val response = dataSource.fetch(
            Request(Method.GET, "/users/$userId/posts")
        )

        return Body.auto<List<Post>>().toLens()(response)
    }
}

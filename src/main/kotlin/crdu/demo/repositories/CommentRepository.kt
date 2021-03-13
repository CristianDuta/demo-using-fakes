package crdu.demo.repositories

import crdu.demo.datasources.DataSource
import crdu.demo.entities.Comment
import org.http4k.core.Body
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.format.Jackson.auto

class CommentRepository(private val dataSource: DataSource) {
    fun findAllForPost(postId: Int): List<Comment> {
        val response = dataSource.fetch(
            Request(Method.GET, "/posts/$postId/comments")
        )

        return Body.auto<List<Comment>>().toLens()(response)
    }
}
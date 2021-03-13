package crdu.demo.handlers

import crdu.demo.entities.Comment
import crdu.demo.events.EventLogger
import crdu.demo.repositories.CommentRepository
import crdu.demo.repositories.PostRepository
import org.http4k.core.*
import org.http4k.format.Jackson.auto
import org.http4k.lens.Path
import org.http4k.lens.int

data class CommentsActivityReport(
    val total: Int,
    val comments: List<Comment>
)

fun commentsActivityReportHandler(
    eventLogger: EventLogger,
    postRepository: PostRepository,
    commentRepository: CommentRepository
): HttpHandler {
    return {
        val userId = Path.int().of("userId")(it)

        val comments: MutableList<Comment> = mutableListOf()
        val posts = postRepository.findAllForUser(userId)

        for (post in posts) {
            comments.addAll(commentRepository.findAllForPost(post.id))
        }
        Response(Status.OK).with(Body.auto<Any>().toLens() of CommentsActivityReport(comments.size, comments))
    }
}

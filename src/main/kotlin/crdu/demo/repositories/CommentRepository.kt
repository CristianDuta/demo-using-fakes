package crdu.demo.repositories

import crdu.demo.datasources.DataSource
import crdu.demo.entities.Comment

class CommentRepository(private val dataSource: DataSource<Comment>) {
    fun findAll(): List<Comment> {
        return dataSource.fetchDataForCollection("comments")
    }

    fun findAllForPost(postId: Int): List<Comment> {
        return dataSource.fetchDataForCollectionInRelationTo("comments", Pair("posts", postId))
    }
}
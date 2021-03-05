package crdu.demo.repositories

import crdu.demo.datasources.DataSource
import crdu.demo.entities.Post

class PostRepository(private val dataSource: DataSource<Post>) {
    fun findAll(): List<Post> {
        return dataSource.fetchDataForCollection("posts")
    }

    fun findAllForUser(userId: Int): List<Post> {
        return dataSource.fetchDataForCollectionInRelationTo("posts", Pair("users", userId))
    }
}

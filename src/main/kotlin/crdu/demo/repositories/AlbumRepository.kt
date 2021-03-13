package crdu.demo.repositories

import crdu.demo.datasources.DataSource
import crdu.demo.entities.Album
import org.http4k.core.Body
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.format.Jackson.auto

class AlbumRepository(private val dataSource: DataSource) {
    fun findAllForUser(userId: Int): List<Album> {
        val response = dataSource.fetch(
            Request(Method.GET, "/users/$userId/albums")
        )

        return Body.auto<List<Album>>().toLens()(response)
    }
}

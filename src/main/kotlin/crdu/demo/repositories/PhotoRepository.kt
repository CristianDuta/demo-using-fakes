package crdu.demo.repositories

import crdu.demo.datasources.DataSource
import crdu.demo.entities.Photo
import org.http4k.core.Body
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.format.Jackson.auto

class PhotoRepository(private val dataSource: DataSource) {
    fun findAllForAlbum(albumId: Int): List<Photo> {
        val response = dataSource.fetch(
            Request(Method.GET, "/albums/$albumId/photos")
        )

        return Body.auto<List<Photo>>().toLens()(response)
    }
}

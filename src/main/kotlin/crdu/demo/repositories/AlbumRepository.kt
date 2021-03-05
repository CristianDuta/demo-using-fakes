package crdu.demo.repositories

import crdu.demo.datasources.DataSource
import crdu.demo.entities.Album

class AlbumRepository(private val dataSource: DataSource<Album>) {
    fun findAll(): List<Album> {
        return dataSource.fetchDataForCollection("albums")
    }

    fun findAllForUser(userId: Int): List<Album> {
        return dataSource.fetchDataForCollectionInRelationTo("albums", Pair("users", userId))
    }
}

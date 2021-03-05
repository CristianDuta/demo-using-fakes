package crdu.demo.repositories

import crdu.demo.datasources.DataSource
import crdu.demo.entities.Photo

class PhotoRepository(private val dataSource: DataSource<Photo>) {
    fun findAll(): List<Photo> {
        return dataSource.fetchDataForCollection("photos")
    }

    fun findAllForAlbum(albumId: Int): List<Photo> {
        return dataSource.fetchDataForCollectionInRelationTo("photos", Pair("albums", albumId))
    }
}
